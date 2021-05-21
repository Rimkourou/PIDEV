package tn.esprit.TuniShow.services;

import com.codename1.ui.events.ActionListener;
import tn.esprit.TuniShow.entity.Reclamation;
import tn.esprit.TuniShow.entity.SalleDeCinema;
import tn.esprit.TuniShow.services.SalleDeCinemaService;
import tn.esprit.TuniShow.utils.Statics;

import java.io.IOException;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class ReclamationService {
    public boolean resultOK;
    private ConnectionRequest req;
    public ArrayList<Reclamation> reclamations;
    public static ReclamationService instance = null;

    public ReclamationService() {
        req = new ConnectionRequest();
    }

    public static ReclamationService getInstance() {
        if (instance == null) {
            instance = new ReclamationService();
        }
        return instance;
    }

    public ArrayList<Reclamation> parseComplains(String jsonText) {
        ArrayList<Reclamation> reclamations = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String, Object> complaintListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) complaintListJson.get("root");
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                System.out.println(obj);
                Reclamation r = new Reclamation();
                r.setId((int) Float.parseFloat(obj.get("id").toString()));
                r.setObjet((obj.get("objet").toString()));
                r.setDescription((obj.get("description").toString()));
                r.setState((obj.get("stete").toString()));

                String sallee = obj.get("salle").toString();
                String salleName = sallee.substring(sallee.indexOf("nom=")+4, sallee.indexOf(',', sallee.indexOf(',')+1));
                r.setSalleName(salleName);
                reclamations.add(r);
            }

        } catch (IOException ex) {

        }

        return reclamations;

    }

    public ArrayList<Reclamation> getAllComplaints() {
        String url = Statics.BASE_URL + "/reclamation/membre/mobile/liste";
        this.req.setUrl(url);
        this.req.setPost(false);
        this.req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reclamations = parseComplains(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reclamations;
    }

    public boolean addReclamation(Reclamation r) {
        String url = Statics.BASE_URL + "/reclamation/membre/mobile/new?objet=" + r.getObjet() + "&description=" + r.getDescription()+ "&stete=" + r.getState()+ "&salle_id=" + r.getIdSalle()+ "&user_id=" + r.getUserId(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si
                la réponse reçue correspond à une autre URL(get par exemple)*/

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

//    public boolean deleteReclamation(int id) {
//        String url = Statics.BASE_URL + "/deleteCategorie?id="+id;
//
//        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//
//                req.removeResponseListener(this);
//
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//
//        return resultOK=true;
//    }
//
//    public boolean modifierCategorie(Reclamation r) {
////        String url = Statics.BASE_URL + "/updateCategorie?id=" + t.getId()+ "&nomcategorie=" + t.getNomcategorie()+"&description=" + t.getDescription();
////        req.setUrl(url);
////
////        req.addResponseListener(new ActionListener<NetworkEvent>() {
////            @Override
////            public void actionPerformed(NetworkEvent evt) {
////                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
////                req.removeResponseListener(this); //Supprimer cet actionListener
////                /* une fois que nous avons terminÃ© de l'utiliser.
////                La ConnectionRequest req est unique pour tous les appels de
////                n'importe quelle mÃ©thode du Service task, donc si on ne supprime
////                pas l'ActionListener il sera enregistrÃ© et donc Ã©xÃ©cutÃ© mÃªme si
////                la rÃ©ponse reÃ§ue correspond Ã  une autre URL(get par exemple)*/
////
////            }
////        });
////        NetworkManager.getInstance().addToQueueAndWait(req);
////        return resultOK;
//        return true;
//    }
}

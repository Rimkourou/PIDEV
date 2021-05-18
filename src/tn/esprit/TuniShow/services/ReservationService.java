package tn.esprit.TuniShow.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import tn.esprit.TuniShow.entity.Film;
import tn.esprit.TuniShow.entity.Reservation;
import tn.esprit.TuniShow.utils.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReservationService {
    public static ReservationService instance=null;
        private ConnectionRequest req;
        public ArrayList<Reservation> reservation;
        public boolean resultOK;
        public static ReservationService getInstance(){
            if(instance==null)
                instance=new ReservationService();
            return instance;
        }
        public ReservationService(){
            req = new ConnectionRequest();
        }

        public boolean addReservation(Reservation r) {
            String url = Statics.BASE_URL + "new?user=" + r.getIduser()+ "&salle=" + r.getIdsalle()+ "&film=" +
                    r.getIdfilm()+ "&place=" + r.getNbrplaceres(); //création de l'URL
            req.setUrl(url);// Insertion de l'URL de notre demande de connexion
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                    req.removeResponseListener(this); //Supprimer cet actionListener
                }
            });
            NetworkManager.getInstance().addToQueueAndWait(req);
            return resultOK;
        }

    public ArrayList<Reservation> parseReservation(String jsonText){
        try {
            reservation = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String,Object> produitListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>) produitListJson.get("root");

            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                Reservation r = new Reservation();
                //r.setId((int)Float.parseFloat(obj.get("id").toString()));

                //java.sql.Date datedereservation = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("datedereservation").toString().substring(0,10)).getTime());
                //r.setDatedereservation(datedereservation);
                r.setIduser((obj.get("iduser").toString()));
                r.setIdsalle((obj.get("idsalle").toString()));
                r.setIdfilm((obj.get("idfilm").toString()));
                r.setNbrplaceres((int)Float.parseFloat(obj.get("nbrplaceres").toString()));
                reservation.add(r);
            }

        } catch (IOException ex) {

        }

        return reservation;

    }

    public ArrayList<Reservation> getAllReservation(){
        String url = Statics.BASE_URL+"apires";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reservation = parseReservation(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reservation;
    }
}


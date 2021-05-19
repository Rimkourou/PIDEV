/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import tn.esprit.TuniShow.entity.Produit;
import tn.esprit.TuniShow.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;

public class CommandeServices {
    
    private ConnectionRequest req;
    public ArrayList<Produit> panier;
    public static CommandeServices instance = null;
     public String session;
    
    private CommandeServices(){
          req = new ConnectionRequest();
     }
     
    public static CommandeServices getInstance() {
        if (instance == null) {
            instance = new CommandeServices();
        }
        return instance;
    }
    
    public ArrayList<Produit> parsePanier(String jsonText){
        try {
            panier = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> produitListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            System.out.println("produitListJson: "+ produitListJson );

            List<Map<String,Object>> list = (List<Map<String,Object>>) produitListJson.get("dataPanier");
//            List<Map<String,Object>> list1 = list.get("dataPanier")

            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                Map<String,Object> obj1 = (Map<String,Object>) obj.get("produit");
//                String qte = obj.get("quantite").toString();
                Produit t = new Produit();
                t.setId((int)Float.parseFloat(obj1.get("id").toString()));
                t.setNom((obj1.get("nom").toString()));
                t.setPrix((float)Float.parseFloat(obj1.get("prix").toString()));
                t.setQte((int)Float.parseFloat(obj.get("quantite").toString()));                
                //t.setIdCategorie((int)Float.parseFloat(obj.get("categorie").toString()));                
                t.setImage((obj1.get("image").toString()));
                
                
                System.out.println("t: "+t);
                panier.add(t);
            }
            
        } catch (IOException ex) {
            
        }
        
        return panier;
    }
    
    public ArrayList<Produit> getPanier(){        
        String url = Statics.BASE_URL+"panierjson";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                panier = parsePanier(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return panier;
    }
    
    
     public void pdf() {
        String url = Statics.BASE_URL + "pdf";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
//                panier = parsePanier(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
//        return panier;
    }
     
     
    
     
    
    
}


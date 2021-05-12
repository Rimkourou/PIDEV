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
import tn.esprit.TuniShow.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author trabe
 */
public class ProduitServices {
    
    private ConnectionRequest req;
    /*public ArrayList<Produit> produit;
    public static ProduitServices instance = null;
    
    private ProduitServices(){
          req = new ConnectionRequest();
     }
     
    public static ProduitServices getInstance() {
        if (instance == null) {
            instance = new ProduitServices();
        }
        return instance;
    }
    
    public ArrayList<Produit> parseProduit(String jsonText){
        try {
            produit = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String,Object> produitListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>) produitListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                Produit t = new Produit();
                //t.setId((int)Float.parseFloat(obj.get("id").toString()));
                t.setNom((obj.get("nom").toString()));
                t.setPrix((float)Float.parseFloat(obj.get("prix").toString()));
                //t.setQte((int)Float.parseFloat(obj.get("qte").toString()));                
                //t.setIdCategorie((int)Float.parseFloat(obj.get("categorie").toString()));                
                t.setImage((obj.get("image").toString()));
                
                produit.add(t);
            }
            
        } catch (IOException ex) {
            
        }

        return produit;
        
    }
    
    public ArrayList<Produit> getAllProduit(){        
        String url = Statics.BASE_URL+"api";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                produit = parseProduit(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return produit;
    }*/
    
}

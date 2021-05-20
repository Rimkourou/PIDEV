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
import com.codename1.l10n.ParseException;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import tn.esprit.TuniShow.entity.Produit;
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
    public ArrayList<Produit> produit;
    public Produit p = new Produit();
    public static ProduitServices instance = null;
    public String session;
    
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
                t.setId((int)Float.parseFloat(obj.get("id").toString()));
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
    
    
    public Produit parseOneProduit(String jsonText) throws ParseException{
            
        Produit t = new Produit();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> obj = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            //t.setId((int)Float.parseFloat(obj.get("id").toString()));
                t.setNom((obj.get("nom").toString()));
                t.setPrix((float)Float.parseFloat(obj.get("prix").toString()));
                //t.setQte((int)Float.parseFloat(obj.get("qte").toString()));                
                //t.setIdCategorie((int)Float.parseFloat(obj.get("categorie").toString()));                
                t.setImage((obj.get("image").toString()));
                
        } catch (IOException ex) {
            
        }
        return t;
    }
    
    public ArrayList<Produit> getAllProduit(){        
        String url = Statics.BASE_URL+"AllProduit";
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
    }
    
    public String addProduitToBusket(String id){        
        String url = Statics.BASE_URL+"add";
        
        req.setUrl(url);
        req.setPost(false);
        req.addArgument("id", id);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                session = new String(req.getResponseData());
//                produit = parseProduit(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  session;
    }
    
    
    
   public ArrayList<Produit> search (String nom){        
        String url = Statics.BASE_URL+"api/search/"+nom;
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
    }
    public Produit getAvrage(){        
        String url = Statics.BASE_URL+"api/stat_avrage";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    p = parseOneProduit(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                } catch (ParseException ex) {
                    
                }
            }
        });
          NetworkManager.getInstance().addToQueueAndWait(req);
        return p;
    }
    
    
    public Produit getHigh(){        
        String url = Statics.BASE_URL+"api/stat_high";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    p = parseOneProduit(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                } catch (ParseException ex) {
                    
                }
            }
        });
          NetworkManager.getInstance().addToQueueAndWait(req);
        return p;
    }
    public Produit getLow(){        
        String url = Statics.BASE_URL+"api/stat_low";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    p = parseOneProduit(new String(req.getResponseData()));
                } catch (ParseException ex) {
                    
                }
                req.removeResponseListener(this);
            }
        });
          NetworkManager.getInstance().addToQueueAndWait(req);
        return p;
    }


    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.services;

import com.codename1.io.*;
import com.codename1.messaging.Message;
import com.codename1.ui.Display;
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
public class PanierServices {

    private ConnectionRequest req;
    public ArrayList<Produit> panier;
    public static PanierServices instance = null;
    public String session;

    private PanierServices() {
        req = new ConnectionRequest();
    }

    public static PanierServices getInstance() {
        if (instance == null) {
            instance = new PanierServices();
        }
        return instance;
    }

    public ArrayList<Produit> parsePanier(String jsonText) {
        try {
            panier = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> produitListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            System.out.println("produitListJson: " + produitListJson);

            List<Map<String, Object>> list = (List<Map<String, Object>>) produitListJson.get("dataPanier");
//            List<Map<String,Object>> list1 = list.get("dataPanier")

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                Map<String, Object> obj1 = (Map<String, Object>) obj.get("produit");
//                String qte = obj.get("quantite").toString();
                Produit t = new Produit();
                t.setId((int) Float.parseFloat(obj1.get("id").toString()));
                t.setNom((obj1.get("nom").toString()));
                t.setPrix((float) Float.parseFloat(obj1.get("prix").toString()));
                t.setQte((int) Float.parseFloat(obj.get("quantite").toString()));
                //t.setIdCategorie((int)Float.parseFloat(obj.get("categorie").toString()));                
                t.setImage((obj1.get("image").toString()));

                System.out.println("t: " + t);
                panier.add(t);
            }

        } catch (IOException ex) {

        }

        return panier;
    }

    public Map<String, Object> parseSession(String jsonText) {
        Map<String, Object> list = null;
        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> produitListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            System.out.println("produitListJson: " + produitListJson);

            list = (Map<String, Object>) produitListJson.get("session");
            System.out.println("list: " + list);

//            Double total = (Double) list.get("total");
//            System.out.println("total: "+total);
//            
//            panier1 = (Map<String, Object>) produitListJson.get("panier");
//            System.out.println("panier: "+panier1);
//            List<Map<String,Object>> list1 = list.get("dataPanier")
//            //Parcourir la liste des tâches Json
//            for(Map<String,Object> obj : list){
//                Map<String,Object> obj1 = (Map<String,Object>) obj.get("panier");
//                Object elet = new Object();
//                elet.((int)Float.parseFloat(obj1.get("id").toString()));
//                t.setNom((obj1.get("nom").toString()));
//                t.setPrix((float)Float.parseFloat(obj1.get("prix").toString()));
//                t.setQte((int)Float.parseFloat(obj.get("quantite").toString()));                
//                //t.setIdCategorie((int)Float.parseFloat(obj.get("categorie").toString()));                
//                t.setImage((obj1.get("image").toString()));
//                
//                
//                System.out.println("t: "+t);
//                panier.add(t);
//            }
        } catch (IOException ex) {

        }

        return list;
    }

    public ArrayList<Produit> getPanier() {
        String url = Statics.BASE_URL + "panierjson";
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

    public String addItemBusket(String id) {
        String url = Statics.BASE_URL + "add_item";

        req.setUrl(url);
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
        return session;
    }

    public String remove(String id) {
        String url = Statics.BASE_URL + "remove";

        req.setUrl(url);

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
        return session;
    }

    public String deleteAll(String id) {
        String url = Statics.BASE_URL + "delete_all";

        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                session = new String(req.getResponseData());
//                produit = parseProduit(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return session;
    }

    public String deleteItem(String id) {
        String url = Statics.BASE_URL + "delete_item";

        req.setUrl(url);
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
        return session;
    }

    public String test() {
//        AsyncResource<Map<String,Object>> fetchJSONAsync(url)
        String url = "https://password-and-captcha-generator.p.rapidapi.com/generateCaptcha?text=123abc&width=160&height=60";
//        com.codename1.util.AsyncResource<Map<String,Object>>.fetchJSONAsync(url)

//       req.fetchJSONAsync(url);
        req.setUrl(url);
        req.addRequestHeader("content-type", "application/x-www-form-urlencoded");
        req.addRequestHeader("x-rapidapi-key", "f90c6d0a3fmsh69919d5b0e10becp168223jsn4f432164bb4a");
        req.addRequestHeader("x-rapidapi-host", "password-and-captcha-generator.p.rapidapi.com");
//        req.addRequestHeader("useQueryString", "true");
//        req.setRequestBody("json-input=%5B%7B%7D%2C%20%5B%22paragraph%22%2C%20%22some%20text%22%5D%5D");

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                session = new String(req.getResponseData());
//                produit = parseProduit(new String(req.getResponseData()));
                System.out.println(session);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return session;
    }

    public void sendMail() {
        Message m = new Message("Body of message");
//        m.getAttachments().put(textAttachmentUri, "text/plain");
//        m.getAttachments().put(imageAttachmentUri, "image/png");
        Display.getInstance().sendMessage(new String[]{"sofiene.gharbi@outlook.com"}, "Subject of message", m);
    }

    public boolean sendMail1() {
        Message m = new Message("<html><body>Check out <a href=\"https://www.codenameone.com/\">Codename One</a></body></html>");
        m.setMimeType(Message.MIME_HTML);

// notice that we provide a plain text alternative as well in the send method
        boolean success = m.sendMessageViaCloudSync("Codename One", "rim.kourou@esprit.tn", "Name Of User", "Message Subject",
                "Check out Codename One at https://www.codenameone.com/");
        return success;
    }

}

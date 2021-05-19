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
import com.codename1.messaging.Message;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.TuniShow.entity.Produit;
import tn.esprit.TuniShow.utils.Statics;
public class ApiServices {

    private ConnectionRequest req;
    public ArrayList<Produit> panier;
    public static ApiServices instance = null;
    public String session;

    private ApiServices() {
        req = new ConnectionRequest();
    }

    public static ApiServices getInstance() {
        if (instance == null) {
            instance = new ApiServices();
        }
        return instance;
    }

    public String sendMail(String emailTo, String toName, String prod_1, float prixProd_1,Integer qteProd_1,String ImProd_1, String prod_2,float prixProd_2,  Integer qteProd_2,String ImProd_2 ) {
//        AsyncResource<Map<String,Object>> fetchJSONAsync(url)
        String url = "https://emailjs.p.rapidapi.com/api/v1.0/email/send";
//        com.codename1.util.AsyncResource<Map<String,Object>>.fetchJSONAsync(url)

//       req.fetchJSONAsync(url);
        req.setUrl(url);
        req.setPost(true);
        req.addRequestHeader("content-type", "application/json");
        req.addRequestHeader("x-rapidapi-key", "f90c6d0a3fmsh69919d5b0e10becp168223jsn4f432164bb4a");
        req.addRequestHeader("x-rapidapi-host", "emailjs.p.rapidapi.com");

        req.setRequestBody("{\r\"accessToken\": \"d2641d7c52c1e0a2ae4e994092ad2499\",\r\"service_id\": \"service_w2sqdx5\",\r\"template_id\": \"template_e8jun36\",\r\"template_params\": {\r\"email_to\": \""+emailTo+"\",\r\"prod_1\": \""+prod_1+"\",\r\"prixProd_1\": \""+prixProd_1+"\",\r\"qteProd_1\": \""+qteProd_1+"\",\r\"ImProd_1\": \""+ImProd_1+"\",\r\"prod_2\": \""+prod_2+"\",\r\"prixProd_2\": \""+prixProd_2+"\",\r\"qteProd_2\": \""+qteProd_2+"\",\r\"ImProd_2\": \""+ImProd_2+"\",\r\"to_name\": \""+toName+"\",\r\"from_name\": \"TuniShow\"\r},\r\"user_id\": \"user_GtIHejmwSCcOrfdWVFCt4\"\r}");
                

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


}

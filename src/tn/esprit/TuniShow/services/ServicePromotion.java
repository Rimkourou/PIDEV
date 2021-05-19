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
import com.codename1.l10n.SimpleDateFormat;

import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import tn.esprit.TuniShow.entity.Promotion;
import tn.esprit.TuniShow.utils.Statics;

public class ServicePromotion {
    
    //singleton
    public static ServicePromotion instance=null;
    //initialisation connection request
    private ConnectionRequest req;
    public ArrayList<Promotion> promotion;
    
    public static ServicePromotion getInstane(){
        if(instance==null)
            instance=new ServicePromotion();
        return instance;
    }
    
    public ServicePromotion(){
        req=new ConnectionRequest();
    }
    
    //ajout
    public void ajoutPromotion(Promotion promotion){
        String url=Statics.BASE_URL+"/promotionA/add?critaire="+promotion.getCritaire()+"&reduction="+promotion.getReduction();
        req.setUrl(url);
        req.addResponseListener(e->{
        
            String str=new String(req.getResponseData());
            System.out.println("data =="+str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    //affichage
    public ArrayList<Promotion> affichagePromotion(){
        ArrayList<Promotion> result=new ArrayList<>();
        String url=Statics.BASE_URL+"/promotionA";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp=new JSONParser();
                try {
                   Map<String,Object>mapPromotion=jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                   
                   List<Map<String,Object>> listOfMaps=(List<Map<String,Object>>) mapPromotion.get("root");
                   
                   for(Map<String,Object> obj :listOfMaps){
                       Promotion pro=new Promotion();
                       
                       float id=Float.parseFloat(obj.get("id").toString());
                       String critaire=obj.get("critaire").toString();
                       String reduction=obj.get("reduction").toString();
                       
                       pro.setId((int)id);
                       pro.setCritaire(critaire);
                       pro.setReduction(reduction);
                       
                       //date
                       String DateConverter=obj.get("date").toString().substring(obj.get("date").toString().indexOf("timestamp") + 10 ,obj.get("obj").toString().lastIndexOf("}"));
                       Date currentTime=new Date(Double.valueOf(DateConverter).longValue() * 1000);
                       
                       SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
                       String dateString=formatter.format(currentTime);
                       pro.setDateD(dateString);
                       pro.setDateF(dateString);
                       
                       //insert data into Arraylist result
                       result.add(pro);
                   }
                       
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        );
        
          NetworkManager.getInstance().addToQueueAndWait(req);
          return result;

        
    }
    //_________________________________________________________________________________________________________
    public ArrayList<Promotion> parsePromotion(String jsonText){
        try {
            promotion = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String,Object> produitListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>) produitListJson.get("root");

            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                Promotion f = new Promotion();
                f.setId((int)Float.parseFloat(obj.get("id").toString()));
                f.setCritaire((obj.get("critaire").toString()));
                f.setDateD((obj.get("dated").toString()));
                f.setDateF((obj.get("datef").toString()));
                f.setCastPhoto((obj.get("castphoto").toString()));
                f.setReduction((obj.get("reduction").toString()));
                
                promotion.add(f);
            }
        } catch (IOException ex) {
        }
        return promotion;
    }

    //
    public ArrayList<Promotion> getAllPromotion(){
        String url = Statics.BASE_URL+"all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ArrayList<Promotion> promotion = parsePromotion(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
 
        return promotion;
    }
    
}

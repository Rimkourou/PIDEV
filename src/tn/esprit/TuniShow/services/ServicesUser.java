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
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import tn.esprit.TuniShow.entity.User;
import tn.esprit.TuniShow.gui.FilmList;
import java.util.Arrays;
import java.util.Map;
import tn.esprit.TuniShow.gui.SessionManager;
import tn.esprit.TuniShow.utils.Statics;


public class ServicesUser {
    
    //singleton
    public static ServicesUser instance=null;
    public static boolean resultOk=true;
    String json;
    
    //connection request
    private ConnectionRequest req;
    
    public ServicesUser() {
        req=new ConnectionRequest();   
    }
    
    public static ServicesUser getInstane(){
        if(instance==null)
            instance=new ServicesUser();
        return instance;
    }
    
//    public boolean addUser(User u){
//        String url=Statics.BASE_URL+"/register/"+u.getNom()+"/"+u.getPrenom()+"/"+u.getEmail()+"/"+u.getPassword();
//        req=new ConnectionRequest(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//               resultOk=req.getResponseCode()==200;
//               req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOk;
//    }
    
    public void signup(TextField nom,TextField prenom,TextField email,TextField password,String age){
        
//        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
//        String strDate=dateFormat.format(age.getDate());
//        System.out.println(strDate);

        String url= Statics.BASE_URL+"user/signup?nom="+nom.getText().toString()+"&prenom="+prenom.getText().toString()+
                "&email="+email.getText().toString()+"&password="+password.getText().toString()+"&age="+age;

       
        req.setUrl(url);
        
        //control saisi
        if(nom.getText().equals(" ") && prenom.getText().equals(" ") && email.getText().equals(" ") && password.getText().equals(" ")){
            Dialog.show("erreur", "veuillez remplir les champs", "ok",null);
        }
        //héthi wa9t tsir excecution ta3 url
        req.addResponseListener(e->{
            //njib data ly7atithom fel form
            byte[]data=(byte[])e.getMetaData();//premiere chose: né5ou id ta3 koll textfield
            String responseData =new String (data);//né5ou content
            System.out.println("data===>"+responseData);
//            System.out.println(age);
//            System.out.println(strDate);
        });
        
        //excecution de la requete w on attend une reponse du serveur
        NetworkManager.getInstance().addToQueueAndWait(req);
        
     
    }

    public void signin(TextField email, TextField password, Resources rs){
        
        String url=Statics.BASE_URL+"user/signin?email="+email.getText().toString()+"&password="+password.getText().toString();
        req.setUrl(url);
        req=new ConnectionRequest(url,false);//false=url not transfered to the server yet
        req.addResponseListener((NetworkEvent e)->{
            JSONParser j=new JSONParser();
            String json=new String(req.getResponseData()) + "";
            try {
                
            if(json.equals("failed")){
              Dialog.show("Echec d'authentification", "Wrong Credentials", "ok",null);  
            }
            else{
                System.out.println("data==>"+json);
                Map<String,Object> user=j.parseJSON(new CharArrayReader(json.toCharArray()));
                
                //session user:bech né5thou les informations mté3 user connecté
                
                float id = Float.parseFloat(user.get("id").toString());
                SessionManager.setId((int)id);//jibt id ta3 user ly3ml login w sajltha fi session ta3i
                
                SessionManager.setPassowrd(user.get("password").toString());
                SessionManager.setNom(user.get("nom").toString()); 
                SessionManager.setPrenom(user.get("prenom").toString());
                SessionManager.setEmail(user.get("email").toString());
                
                
                System.out.println("current user==>"+SessionManager.getId()+","+SessionManager.getNom()+","
                        +SessionManager.getPrenom()+","+SessionManager.getEmail()+","+SessionManager.getRole()+","+SessionManager.getState());
                
                if(user.size()>0)//l9a user
                    new FilmList(rs).show();
                
                //System.out.println(user.values()[1]);
//                //zédtou ena
//               
//                User user2=null;
//                user2=new User();
//                //user2.setId(json.getInteger(0));
//                String u=new String(req.getResponseData());
//                user2.setNom(u.substring(17, 19));
//                user2.setPrenom(u.substring(2, 3));
//                // user.setAge(r.getInteger(3));
//                user2.setEmail(user2.getEmail());
//                user2.setPassword(user2.getRole());
//                System.out.println(user2);
            }
            
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        //excecution de la requete w on attend une reponse du serveur
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public String getPasswordByEmail(String email,Resources rs){
        String url=Statics.BASE_URL+"user/getPasswordByEmail?email="+email;
        req.setUrl(url);
        
        
        req.addResponseListener(a->{
                JSONParser j=new JSONParser();
                json=new String(req.getResponseData()) + "";
            
            try {
                
                System.out.println("data==>"+json);
                
                Map<String,Object> password=j.parseJSON(new CharArrayReader(json.toCharArray()));

            
            } catch (Exception ex) {
                ex.printStackTrace();
            }
          
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println("***************************");
        System.out.println(json);
        System.out.println("****************************");
        return json;
    }
    
    public boolean modifyProfile(User user/*TextField nom,TextField prenom,TextField email,TextField password*/){
        
        String url=Statics.BASE_URL+"/Update?nom="+user.getNom()+"&prenom="+user.getPrenom()+
          "&email="+user.getEmail()+"&password="+user.getPassword();

        req.setUrl(url);
        
//        SessionManager.setPassowrd(user.getPassword());
//        SessionManager.setNom(user.getNom()); 
//        SessionManager.setPrenom(user.getPrenom());
//        SessionManager.setEmail(user.getEmail());
                
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk=req.getResponseCode()==200; //code response http 200 ok
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
        
    }
    
    
    
}

package tn.esprit.TuniShow.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import tn.esprit.TuniShow.entity.Film;
import tn.esprit.TuniShow.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilmService {
    private ConnectionRequest req;
    public ArrayList<Film> films;
    public static FilmService instance = null;

    public FilmService(){
          req = new ConnectionRequest();
     }

    public static FilmService getInstance() {
        if (instance == null) {
            instance = new FilmService();
        }
        return instance;
    }

    public ArrayList<Film> parseFilm(String jsonText){
        try {
            films = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String,Object> produitListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>) produitListJson.get("root");

            //Parcourir la liste des tâches Json
            for(Map<String,Object> obj : list){
                Film f = new Film();
                f.setId((int)Float.parseFloat(obj.get("id").toString()));
                f.setTitre((obj.get("titre").toString()));
                f.setDescription((obj.get("description").toString()));
                f.setAuteur((obj.get("auteur").toString()));
                f.setCategorie((obj.get("categorie").toString()));
                f.setGenre((obj.get("genre").toString()));
                f.setImg((obj.get("img").toString()));
                films.add(f);
            }
        } catch (IOException ex) {
        }
        return films;
    }

    public ArrayList<Film> getAllFilm(){
        String url = Statics.BASE_URL+"apifilm";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                films = parseFilm(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return films;
    }

    public  Film DetailFilm(int id, Film film){
        String url=Statics.BASE_URL+"/show?"+id;
        req.setUrl(url);
        String str = new String(req.getResponseData());
        req.addResponseListener(((evt)->{
           JSONParser jsonParser = new JSONParser();
           try {
               Map<String,Object> obj = jsonParser.parseJSON(new CharArrayReader(new String(str).toCharArray()));
               film.setTitre(obj.get("titre").toString());
               film.setDescription(obj.get("description").toString());
               film.setAuteur(obj.get("auteur").toString());
               film.setCategorie(obj.get("categorie").toString());
               film.setGenre(obj.get("genre").toString());
               film.setImg(obj.get("img").toString());
           }catch (IOException ex){
               System.out.println("error related to sql"+ex.getMessage());
           }
            System.out.println("data"+str);
        }));
        NetworkManager.getInstance().addToQueueAndWait(req);
        return film;
    }
}

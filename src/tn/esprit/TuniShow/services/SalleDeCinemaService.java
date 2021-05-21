package tn.esprit.TuniShow.services;

import com.codename1.ui.events.ActionListener;
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

public class SalleDeCinemaService {
    private ConnectionRequest req;
    public ArrayList<SalleDeCinema> salleDeCinema;
    public static SalleDeCinemaService instance = null;

    public SalleDeCinemaService() {
        req = new ConnectionRequest();
    }

    public static SalleDeCinemaService getInstance() {
        if (instance == null) {
            instance = new SalleDeCinemaService();
        }
        return instance;
    }

    public ArrayList<SalleDeCinema> parseMovieRooms(String jsonText) {
        ArrayList<SalleDeCinema> salle = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> salleListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) salleListJson.get("root");

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                SalleDeCinema t = new SalleDeCinema();
                t.setId((int) Float.parseFloat(obj.get("id").toString()));
                t.setNom((obj.get("nom").toString()));
                t.setDescription((obj.get("Description").toString()));
                t.setAdresse((obj.get("adresse").toString()));
                salle.add(t);
            }

        } catch (IOException ex) {

        }

        return salle;

    }

    public ArrayList<SalleDeCinema> getAllMovieRooms() {
        String url = Statics.BASE_URL + "/salle/de/cinema/membreAll";
        this.req.setUrl(url);
        this.req.setPost(false);
        this.req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                salleDeCinema = parseMovieRooms(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return salleDeCinema;
    }

}

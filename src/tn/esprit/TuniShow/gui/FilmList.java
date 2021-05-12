package tn.esprit.TuniShow.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.TuniShow.entity.Film;
import tn.esprit.TuniShow.services.FilmService;

import java.io.IOException;
import java.util.ArrayList;

public class FilmList extends Form {
    Form current;
    Image img = null;
    ImageViewer iv = null;
    EncodedImage ec;

    public FilmList(Resources res) {
        current = this;
        ArrayList<Film> films = FilmService.getInstance().getAllFilm();
        for(Film f : films){
//          Container cnHor = new Container(BoxLayout.x());
            Container cnVer = new Container(BoxLayout.y());
            String url = "http://127.0.0.1:8000/dist/img/"+f.getImg();
            try {
                ec = EncodedImage.create("/load.jpeg");
                img = URLImage.createToStorage(ec, url, url,URLImage.RESIZE_SCALE);
                iv = new ImageViewer(img);
                cnVer.add(iv);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            Label lbType = new Label(f.getTitre());
            cnVer.add(lbType);
//            cnHor.add(cnVer);
            add(cnVer);
        }
    }
}

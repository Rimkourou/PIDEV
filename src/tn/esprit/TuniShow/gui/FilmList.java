package tn.esprit.TuniShow.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.TuniShow.entity.Film;
import tn.esprit.TuniShow.entity.Reservation;
import tn.esprit.TuniShow.services.FilmService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class FilmList extends SideMenuBaseForm {
    Form current;
    Image img = null;
    ImageViewer imageViewer = null;
    EncodedImage ec;
    FilmService fs = new FilmService();
    ArrayList<Film> films = new ArrayList<>();
    public FilmList(Resources res) {
        current = this;
        setTitle("Movies");
        setLayout(BoxLayout.y());
        setScrollableY(true);
        films= fs.getAllFilm();
        showFilmList();

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_MENU, e -> getToolbar().openSideMenu());
        setupSideMenu(res);

            getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : getContentPane()) {
                    Container mb = (Container) cmp;
                    String line1= mb.getUIID();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);
                }
                getContentPane().animateLayout(150);
            }
        }, 4);
        removeAll();
        Collections.sort(films, Film.titleComparator);
        showFilmList();
    }

    public void showFilmList(){
        for(Film f : films){
            String url = "http://127.0.0.1:8000/dist/img/"+f.getImg();
            Container m = new Container();
            m.setUIID(f.getTitre());

            int deviceWidth = Display.getInstance().getDisplayWidth();
            Image placeholder = Image.createImage(deviceWidth-350, deviceWidth-150, 0xbfc9d2);
            //EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
            ec = EncodedImage.createFromImage(placeholder,false);
            img = URLImage.createToStorage(ec, url, url,URLImage.RESIZE_SCALE_TO_FILL);
            imageViewer = new ImageViewer(img);
            ScaleImageButton fillButton = new ScaleImageButton(img);
            fillButton.addActionListener(l -> new FilmDetails(current, f).show());
            m.addAll(fillButton);
            add(m);
        }
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}

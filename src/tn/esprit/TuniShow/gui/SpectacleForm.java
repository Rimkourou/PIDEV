package tn.esprit.TuniShow.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.TuniShow.entity.Spectacle;
import tn.esprit.TuniShow.services.SpectacleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SpectacleForm extends SideMenuBaseForm {
    Resources res;
    Form current;
    Image img = null;
    ImageViewer imageViewer = null;
    EncodedImage ec;
    SpectacleService spectacleService = new SpectacleService();
    ArrayList<Spectacle> spectacleArrayList = new ArrayList<>();
    public SpectacleForm(Resources res) {
        /* *** *CONFIG SCREEN* *** */
        current = this;
        setTitle("Spectacle List");
        setLayout(BoxLayout.y());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_MENU, e -> getToolbar().openSideMenu());
        setupSideMenu(res);

        /* *** *YOUR CODE GOES HERE* *** */
        spectacleArrayList = spectacleService.showAll();
        Collections.reverse(spectacleArrayList);
        showSpectacle();
        /* *** *SEARCHBAR* *** */
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
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                            line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);

                }
                getContentPane().animateLayout(150);
            }
        }, 4);
        /* *** *OVERFLOW MENU* *** */
        getToolbar().addCommandToOverflowMenu("Sort by Name", null, (evt)->{
            removeAll();
            Collections.sort(spectacleArrayList, Spectacle.titleComparator);
            showSpectacle();
        });
        /* *** *SIDE MENU* *** */
        getToolbar().addCommandToLeftSideMenu("", null, (evt) -> {        });
        getToolbar().addCommandToLeftSideMenu("Shows", null, (evt) -> {            new SpectacleForm(res).show();        });
        getToolbar().addCommandToLeftSideMenu("Planning", null, (evt) -> {            new PlanningsForm(res).show();        });

    }
    public void showSpectacle(){
        for (Spectacle spectacle : spectacleArrayList) {
            String url = "http://127.0.0.1:8000/dist/img/"+spectacle.getImage();
            int deviceWidth = Display.getInstance().getDisplayWidth();
            Image placeholder = Image.createImage(deviceWidth / 3, deviceWidth / 4, 0xbfc9d2);
            ec = EncodedImage.createFromImage(placeholder,false);
            img = URLImage.createToStorage(ec, url, url,URLImage.RESIZE_SCALE);
            imageViewer = new ImageViewer(img);
            //Image image = URLImage.createToStorage(ec, spectacle.getTitre() + spectacle.getId(), spectacle.getImage(), URLImage.RESIZE_SCALE);
            MultiButton multiButton = new MultiButton();
            multiButton.setTextLine1(spectacle.getTitre() + "");
            multiButton.setTextLine2(spectacle.getGenre() + "");
            multiButton.setTextLine3(spectacle.getDate()+"");
            multiButton.setIcon(img);
            multiButton.setUIID(spectacle.getId() + "");
            multiButton.addActionListener(l -> new ShowSpectacle(current, spectacle).show());
            add(multiButton);
        }
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}

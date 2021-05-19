/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;

import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.TuniShow.entity.Promotion;
import tn.esprit.TuniShow.services.ServicePromotion;

import java.util.ArrayList;

public class ListPromotionForm extends SideMenuBaseForm {

    Form current;
    Image img = null;
    ImageViewer imageViewer = null;
    EncodedImage ec;
    ServicePromotion fs = new ServicePromotion();
    ArrayList<Promotion> promotion = new ArrayList<>();
    
    public ListPromotionForm(Resources res) {
        current = this;
        setTitle("Promotions");
        setLayout(BoxLayout.y());
        setScrollableY(true);
        promotion= fs.getAllPromotion();
        showPromotionList();

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
        
        showPromotionList();
    }

    public void showPromotionList(){
        
        for(Promotion f : promotion){
        
            String url = "http://127.0.0.1:8000/dist/img/"+f.getCastPhoto();
            
            Container m = new Container(new BorderLayout());
            //m.setUIID(f.getReduction());

            int deviceWidth = Display.getInstance().getDisplayWidth();
            Image placeholder = Image.createImage(deviceWidth-350, deviceWidth-150, 0xbfc9d2);
            
            ec = EncodedImage.createFromImage(placeholder,false);
            img = URLImage.createToStorage(ec, url, url,URLImage.RESIZE_SCALE_TO_FILL);
            imageViewer = new ImageViewer(img);
            ScaleImageButton fillButton = new ScaleImageButton(img);
            //fillButton.addActionListener(l -> new FilmDetails(current, f).show());
            m.add(BorderLayout.CENTER,fillButton);
            add(m);
            
        }
    }

    @Override
    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
}
/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package tn.esprit.TuniShow.gui;

import com.codename1.components.MultiButton;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import tn.esprit.TuniShow.entity.Planning;
import tn.esprit.TuniShow.entity.Spectacle;
import tn.esprit.TuniShow.services.PlanningService;
import tn.esprit.TuniShow.services.SpectacleService;

import java.util.ArrayList;

/**
 * Common code that can setup the side menu
 *
 * @author Shai Almog
 */
public abstract class SideMenuBaseForm extends Form {
    Form current=this;
    Spectacle s;

    public SideMenuBaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseForm(String title) {
        super(title);
    }

    public SideMenuBaseForm() {
    }

    public SideMenuBaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }
    
    public void setupSideMenu(Resources res) {
        Image profilePic = res.getImage("user-picture.jpg");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("  Wifek Fouzai", profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");
        
        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("  Movie Room", FontImage.MATERIAL_WEEKEND,  e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Movies", FontImage.MATERIAL_LOCAL_MOVIES,  e -> new FilmList(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Shows", FontImage.MATERIAL_LIVE_TV,  e -> new SpectacleForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Promotions", FontImage.MATERIAL_LOCAL_OFFER,  e -> new ListPromotionForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Planning", FontImage.MATERIAL_EVENT,  e -> new PlanningsForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Booking", FontImage.MATERIAL_EVENT_NOTE,  e -> new ReservationList(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Shop", FontImage.MATERIAL_LOCAL_MALL,  e ->new AfficherListProduit().show());
        getToolbar().addMaterialCommandToSideMenu("  Complaints", FontImage.MATERIAL_REPORT,  e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Profile", FontImage.MATERIAL_PEOPLE,  e -> new ProfileForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP,  e -> new LoginForm(res).show());
    }
    
    protected abstract void showOtherForm(Resources res);
}

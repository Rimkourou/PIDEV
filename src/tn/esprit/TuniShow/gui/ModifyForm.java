/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.gui;

import com.codename1.ui.Form;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import tn.esprit.TuniShow.entity.User;
import tn.esprit.TuniShow.services.ServicesUser;


class ModifyForm extends SideMenuBaseForm{
   private Button modify;
   private TextField email;
   private TextField password;
   private TextField nom;
   private TextField prenom;
    public ModifyForm(Resources res, User user) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("user-picture.jpg");
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        //---------------------
        
        String pr=SessionManager.getPrenom();
        
        String n=SessionManager.getNom();
        
        String e=SessionManager.getEmail();
        
        
        //---------------------
        Container titleCmp = BoxLayout.encloseY(
                        FlowLayout.encloseIn(menuButton),
                        BorderLayout.centerAbsolute(
                                BoxLayout.encloseX(
                                    new Label(pr, "Title"),
                                    new Label(n, "Title")
                                )
                            ).add(BorderLayout.WEST, profilePicLabel)
                        
                );
        
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Style.UNIT_TYPE_PIXELS);
        tb.setTitleComponent(fab.bindFabToContainer(titleCmp));    
        
        
        add(this.nom=new TextField(user.getNom()));

        add(this.prenom=new TextField(user.getPrenom()));
           
        add(this.email=new TextField("",user.getEmail(), 20, TextField.EMAILADDR));
       
        add(this.password=new TextField("", user.getPassword(), 1,TextField.PASSWORD));
        
        

        modify=new Button("Modify Informations");
        modify.setUIID("LoginButton");
        add(modify);
        modify.addPointerPressedListener(l->{
            
            user.setNom(nom.getText());
            user.setPrenom(prenom.getText());
            user.setEmail(email.getText());
            user.setPassword(password.getText());
            
            
        //appel fonction modifier 
        if(ServicesUser.getInstane().modifyProfile(user)){
            Dialog.show("Success", "modified successfully", new Command("OK"));
            new ProfileForm(res).show();
        }
        });
        
//            System.out.println("**************");
//            System.out.println(nom.getText());
//            System.out.println("**************");
        
        Button btnAnnuler=new Button("Annuler");
        add(btnAnnuler);
        btnAnnuler.addActionListener(t->{
            new ProfileForm(res).show();
        });

        
         setupSideMenu(res);

    }
    
    private void addButtonBottom(String text, int color, boolean first) {
        MultiButton finishLandingPage = new MultiButton(text);
        finishLandingPage.setUIID("Container");
        finishLandingPage.setUIIDLine1("TodayEntry");
        finishLandingPage.setIcon(createCircleLine(color, finishLandingPage.getPreferredH(),  first));
        finishLandingPage.setIconUIID("Container");
        add(FlowLayout.encloseIn(finishLandingPage));
    }
    
    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if(first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }

    protected void showOtherForm(Resources res) {
        new StatsForm(res).show();
    }
    
    
}

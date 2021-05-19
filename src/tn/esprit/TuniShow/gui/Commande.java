/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import tn.esprit.TuniShow.entity.Produit;
import tn.esprit.TuniShow.services.ApiServices;
import tn.esprit.TuniShow.services.CommandeServices;
import tn.esprit.TuniShow.services.PanierServices;
import tn.esprit.TuniShow.services.ProduitServices;
import tn.esprit.TuniShow.utils.Statics;


public class Commande extends Form {

        Form current;
    Image img = null;
    ImageViewer iv = null;
    EncodedImage ec;

    public Commande() {
        current = this;
        setLayout(BoxLayout.y());

        getToolbar().addCommandToRightBar("Shop", null, t -> new AfficherListProduit().show());

        Container cnVerRoot = new Container(BoxLayout.y());
        ArrayList<Produit> pd = PanierServices.getInstance().getPanier();
        System.out.println("pd: " + pd);
        
        Container cnHorBtn = new Container(BoxLayout.x());

        for (Produit p : pd) {
            Container cnVer = new Container(BoxLayout.y());
            cnVer.setName("" + p.getId());
            Container cnHor = new Container(BoxLayout.x());
            cnHor.setName("" + p.getId());
            Container cnHor1 = new Container(BoxLayout.x());

            //Add image
            String url = Statics.BASE_URL + "/img/" + p.getImage();
            Label l = new Label();

            try {
                ec = EncodedImage.create("/load.png");
                img = URLImage.createToStorage(ec, url, url, URLImage.RESIZE_SCALE);
                iv = new ImageViewer(img);
                cnHor.add(iv);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            SpanLabel lbID = new SpanLabel("" + p.getId());
            Label lbType = new Label(p.getNom());
            SpanLabel lbPrix = new SpanLabel("" + p.getPrix());
            SpanLabel lbQte = new SpanLabel("" + p.getQte());

            

           

            cnVer.add(lbID);
            cnVer.add(lbType);
            cnVer.add(lbPrix);
          
            
            
            cnVer.add(lbQte);

            cnHor.add(cnVer);
            cnVerRoot.add(cnHor);

        }

        Button ConfComm = new Button("Conf. Com.");
//            PasserCommande.setAutoSizeMode(focusScrolling);
        FontImage.setMaterialIcon(ConfComm, FontImage.MATERIAL_ADD, 4);
        Button downFacture = new Button("Télé. Fact.");
//            PasserCommande.setAutoSizeMode(focusScrolling);
        FontImage.setMaterialIcon(downFacture, FontImage.MATERIAL_SAVE, 4);

        
        
        ConfComm.addActionListener((evt) -> {
                ApiServices.getInstance().sendMail("rim.kourou@esprit.tn", "Rim Kourou",pd.get(0).getNom(),pd.get(0).getPrix(),pd.get(0).getQte(),pd.get(0).getImage(),pd.get(1).getNom(),pd.get(1).getPrix(),pd.get(1).getQte(),pd.get(1).getImage());
                Dialog.show("Alert", "Votre email a été bien envoyée. Merci de vérifier votre boite de réception", new Command("D'accord"));
//                (t -> new Commande().show())
        });
        
        downFacture.addActionListener((evt) -> {
                CommandeServices.getInstance().pdf();
//                Dialog.show("Alert", "Votre email a été bien envoyée. Merci de vérifier votre boite de réception", new Command("D'accord"));
////                (t -> new Commande().show())
        });
        cnHorBtn.add(ConfComm);
        cnHorBtn.add(downFacture);
        cnVerRoot.add(cnHorBtn);
        add(cnVerRoot);
    }

}

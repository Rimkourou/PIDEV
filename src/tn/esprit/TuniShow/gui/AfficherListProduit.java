/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.TuniShow.entity.Produit;
import tn.esprit.TuniShow.services.ProduitServices;

import java.io.IOException;
import java.util.ArrayList;

import static tn.esprit.TuniShow.utils.Statics.BASE_URL;

public class AfficherListProduit extends Form {

    Form current;
    Image img = null;
    ImageViewer iv = null;
    EncodedImage ec;

    public AfficherListProduit() {
        current = this;

        getToolbar().addCommandToRightBar("Panier", null, t
                -> new Panier().show());
        
        
        
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_SEARCH, e-> new SearchMenuForm(current).show());
       

        //menuButton.addActionListener(e -> getToolbar().openSideMenu());
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_PIE_CHART, e-> new PieChart(current).show());

        

        ArrayList<Produit> pd = ProduitServices.getInstance().getAllProduit();
        for (Produit p : pd) {
//            Container cnHor = new Container(BoxLayout.x());
            Container cnVer = new Container(BoxLayout.y());

            //Add image
            String url = BASE_URL + "/img/" + p.getImage();
            try {
                ec = EncodedImage.create("/load.png");
                img = URLImage.createToStorage(ec, url, url, URLImage.RESIZE_SCALE);
                iv = new ImageViewer(img);
                cnVer.add(iv);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            Label lbType = new Label(p.getNom());
            SpanLabel lbID = new SpanLabel("" + p.getId());
            SpanLabel lbDesc = new SpanLabel("" + p.getPrix());
            Button ajouterAuPanier = new Button("Ajouter au panier");
            ajouterAuPanier.setName("" + p.getId());
            FontImage.setMaterialIcon(ajouterAuPanier, FontImage.MATERIAL_ADD, 4);

            ajouterAuPanier.addActionListener((evt) -> {
                String session = ProduitServices.getInstance().addProduitToBusket(evt.getComponent().getName());
//                Dialog.show("Produit", "Vous êtes sur le panier " + evt.getComponent().getName() + "\n" + session, new Command("Oui"));
                Dialog.show("Alert", "Votre produit dont l'id "+  evt.getComponent().getName()  +" a été ajouté avec succés au panier", new Command("D'accord"));
            });

            cnVer.add(lbID);
            cnVer.add(lbType);
            cnVer.add(lbDesc);
            cnVer.add(ajouterAuPanier);
//            cnHor.add(cnVer);
            add(cnVer);
        }

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import tn.esprit.TuniShow.entity.Produit;
import tn.esprit.TuniShow.services.ProduitServices;
import java.io.IOException;
import java.util.ArrayList;
import tn.esprit.TuniShow.services.ProduitServices;


public class AfficherListProduitsearch extends Form{
    Form current;
    Image img = null;
    ImageViewer iv = null;
    EncodedImage ec;
    
    
    
    
   
    public AfficherListProduitsearch(Form previous,String nom) {
        current = this;
        
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
        
        ArrayList<Produit> pd = ProduitServices.getInstance().search(nom);
        for(Produit p : pd){
//            Container cnHor = new Container(BoxLayout.x());
            Container cnVer = new Container(BoxLayout.y());
            
            //Add image
            String url = "http://127.0.0.1:8000/img/"+p.getImage();
            try {
                ec = EncodedImage.create("/load.png");
                img = URLImage.createToStorage(ec, url, url,URLImage.RESIZE_SCALE);
                iv = new ImageViewer(img);
                cnVer.add(iv);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            
            Label lbType = new Label(p.getNom());
            SpanLabel lbDesc = new SpanLabel(""+ p.getPrix());
             Button ajouterAuPanier = new Button("Ajouter au panier");
            ajouterAuPanier.setName("" + p.getId());
            FontImage.setMaterialIcon(ajouterAuPanier, FontImage.MATERIAL_ADD, 4);

            ajouterAuPanier.addActionListener((evt) -> {
                String session = ProduitServices.getInstance().addProduitToBusket(evt.getComponent().getName());
//                Dialog.show("Produit", "Vous êtes sur le panier " + evt.getComponent().getName() + "\n" + session, new Command("Oui"));
                Dialog.show("Alert", "Votre produit dont l'id "+  evt.getComponent().getName()  +" a été ajouté avec succés au panier", new Command("D'accord"));
            });
            cnVer.add(lbType);
            cnVer.add(lbDesc);
            cnVer.add(ajouterAuPanier);
//            cnHor.add(cnVer);
            add(cnVer); 
        }
    }
}

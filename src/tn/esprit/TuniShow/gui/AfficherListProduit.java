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
import tn.esprit.TuniShow.services.ProduitServices;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author trabe
 */
public class AfficherListProduit extends Form{
    Form current;
    Image img = null;
    ImageViewer iv = null;
    EncodedImage ec;
    
    public AfficherListProduit() {
        current = this;
        
        /*ArrayList<Produit> pd = ProduitServices.getInstance().getAllProduit();
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
            Button voirMenu = new Button("ajouter au panier");
            FontImage.setMaterialIcon(voirMenu, FontImage.MATERIAL_ADD, 4);
            
            voirMenu.addActionListener((evt) -> {
                Dialog.show("Produit", "Vous êtes sur le panier "+p.getNom(), new Command("Oui"));
            });
            
            cnVer.add(lbType);
            cnVer.add(lbDesc);
            cnVer.add(voirMenu);
//            cnHor.add(cnVer);
            add(cnVer); 
        }*/
    }
}

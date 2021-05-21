package tn.esprit.TuniShow.gui;

import com.codename1.io.*;
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
import tn.esprit.TuniShow.entity.SalleDeCinema;
import tn.esprit.TuniShow.services.SalleDeCinemaService;

import java.io.IOException;
import java.util.ArrayList;

import com.codename1.components.ToastBar;
import tn.esprit.TuniShow.utils.Statics;

import static tn.esprit.TuniShow.utils.Statics.BASE_URL;

public class SalleDeCinemaListe extends Form {
    Form current;
    Image img = null;
    ImageViewer iv = null;
    EncodedImage ec;

    public SalleDeCinemaListe() {
        current = this;


        ArrayList<SalleDeCinema> salleDeCinema = SalleDeCinemaService.getInstance().getAllMovieRooms();
        for (SalleDeCinema s : salleDeCinema) {
//            Container cnHor = new Container(BoxLayout.x());
            Container cnVer = new Container(BoxLayout.y());


            SpanLabel lbID = new SpanLabel("" + s.getId());
            Label lbType = new Label(s.getNom());
            Label lbDesc = new Label("" + s.getDescription());
            Label lbAdr = new Label("" + s.getAdresse());
//            Button ajouterAuPanier = new Button("Ajouter au panier");
//            ajouterAuPanier.setName("" + p.getId());
//            FontImage.setMaterialIcon(ajouterAuPanier, FontImage.MATERIAL_ADD, 4);

//            ajouterAuPanier.addActionListener((evt) -> {
//                String session = ProduitServices.getInstance().addProduitToBusket(evt.getComponent().getName());
////                String urlTest = BASE_URL+ "addP/"+evt.getComponent().getName();
////                Dialog.show("Produit", "Vous êtes sur le panier "+p.getNom(), new Command("Oui"));
//                Dialog.show("Produit", "Vous êtes sur le panier " + evt.getComponent().getName() + "\n" + session, new Command("Oui"));
////                Dialog.show("Produit", "Vous êtes sur le panier " + evt.getComponent(), new Command("Oui"));
//
//            });

            cnVer.add(lbID);
            cnVer.add(lbType);
            cnVer.add(lbDesc);
            cnVer.add(lbAdr);
            add(cnVer);
        }

    }
}

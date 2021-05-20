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

/**
 *
 * @author user
 */
public class Panier extends Form {

    Form current;
    Image img = null;
    ImageViewer iv = null;
    EncodedImage ec;

    public Panier() {
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

//            try {
//                ec = EncodedImage.create("/load.png");
//                String base64ImageData = "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";
//                byte[] b = com.codename1.util.Base64.decode(base64ImageData.getBytes());
//                img = EncodedImage.create(b);
//                iv = new ImageViewer(img);
//                cnVerRoot.add(iv);
//            } catch (IOException ex) {
//                System.out.println(ex.getMessage());
//            }

            SpanLabel lbID = new SpanLabel("" + p.getId());
            Label lbType = new Label(p.getNom());
            SpanLabel lbPrix = new SpanLabel("" + p.getPrix());
            SpanLabel lbQte = new SpanLabel("" + p.getQte());

            Button ajouter = new Button("");
            ajouter.setName("" + p.getId());
            FontImage.setMaterialIcon(ajouter, FontImage.MATERIAL_ADD, 3);

            Button remove = new Button("");
            remove.setName("" + p.getId());
            FontImage.setMaterialIcon(remove, FontImage.MATERIAL_REMOVE, 3);

            Button deleteItem = new Button("Vider");
            deleteItem.setName("" + p.getId());
            FontImage.setMaterialIcon(deleteItem, FontImage.MATERIAL_DELETE, 3);

            cnVer.add(lbID);
            cnVer.add(lbType);
            cnVer.add(lbPrix);
            cnHor1.add(remove);
            cnHor1.add(lbQte);
            cnHor1.add(ajouter);
            cnHor1.add(deleteItem);
            cnVer.add(cnHor1);

            cnHor.add(cnVer);
            cnVerRoot.add(cnHor);

            ajouter.addActionListener((evt) -> {
                String session = PanierServices.getInstance().addItemBusket(evt.getComponent().getName());
                Map<String, Object> sess = PanierServices.getInstance().parseSession(session);
                Map<String, Object> panier = (Map<String, Object>) sess.get("panier");
                String idP = (String) evt.getComponent().getName();
                String qte = String.valueOf(panier.get(idP));
                lbQte.setText(qte.substring(0, qte.indexOf('.')));

                
                 
//                System.out.println(ss);
//                HttpResponse<String> response = Unirest.post("https://yogthos.p.rapidapi.com/")
//	.header("content-type", "application/x-www-form-urlencoded")
//	.header("x-rapidapi-key", "f90c6d0a3fmsh69919d5b0e10becp168223jsn4f432164bb4a")
//	.header("x-rapidapi-host", "yogthos.p.rapidapi.com")
//	.body("json-input=%5B%7B%7D%2C%20%5B%22paragraph%22%2C%20%22some%20text%22%5D%5D")
//	.asString();

            });

            remove.addActionListener((evt) -> {
                String session = PanierServices.getInstance().remove(evt.getComponent().getName());
                Map<String, Object> sess = PanierServices.getInstance().parseSession(session);
                Map<String, Object> panier = (Map<String, Object>) sess.get("panier");
                String idP = (String) evt.getComponent().getName();
                String qte = String.valueOf(panier.get(idP));
                lbQte.setText(qte.substring(0, qte.indexOf('.')));
            });

            deleteItem.addActionListener((evt) -> {
                String session = PanierServices.getInstance().deleteItem(evt.getComponent().getName());
//                Map<String, Object> sess = PanierServices.getInstance().parseSession(session);
//                Map<String, Object> panier = (Map<String, Object>) sess.get("panier");
                cnVerRoot.removeComponent(deleteItem.getParent().getParent().getParent());

                new Panier().show();
            });
        }

        Button deleteAll = new Button("Vider");
        FontImage.setMaterialIcon(deleteAll, FontImage.MATERIAL_DELETE, 4);
        Button PasserCommande = new Button("Passer Cmd.");
//            PasserCommande.setAutoSizeMode(focusScrolling);
        FontImage.setMaterialIcon(PasserCommande, FontImage.MATERIAL_ADD, 4);

        if (pd.isEmpty()) {
            deleteAll.setEnabled(false);
            PasserCommande.setEnabled(false);
        }

        PasserCommande.addActionListener((t -> new Commande().show())
        );
        deleteAll.addActionListener((evt) -> {
            String session = PanierServices.getInstance().deleteAll(evt.getComponent().getName());
//                Map<String, Object> sess = PanierServices.getInstance().parseSession(session);
//                Map<String, Object> panier = (Map<String, Object>) sess.get("panier");
            cnVerRoot.removeAll();

            new AfficherListProduit().show();
        });

        cnHorBtn.add(deleteAll);
        cnHorBtn.add(PasserCommande);
        cnVerRoot.add(cnHorBtn);
        add(cnVerRoot);
    }

}

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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.table.DefaultTableModel;
import com.codename1.ui.table.TableModel;
import tn.esprit.TuniShow.entity.Reclamation;
import tn.esprit.TuniShow.entity.SalleDeCinema;
import tn.esprit.TuniShow.services.ReclamationService;
import tn.esprit.TuniShow.services.SalleDeCinemaService;

import java.io.IOException;
import java.util.ArrayList;

import com.codename1.components.ToastBar;
import tn.esprit.TuniShow.utils.Statics;

import static tn.esprit.TuniShow.utils.Statics.BASE_URL;

public class ReclamationListe extends Form {
    Form previous;
    Form current;

    public ReclamationListe() {
        setTitle("Reclamation Liste");
        setLayout(BoxLayout.y());
        current = this;
        Form hi = new Form("Layout Animations", new BoxLayout(BoxLayout.Y_AXIS));
        Button btnValider = new Button("Add Reclamation");
        Button btnUpdate = new Button("update");
        Button btnDelete = new Button("delete");

        add(btnValider);
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AddReclamation().show();
            }
        });
        ArrayList<Reclamation> reclamations = ReclamationService.getInstance().getAllComplaints();
        for (int i=0; i<reclamations.size(); i++) {
            Container cnVer = new Container(BoxLayout.y());
//            Container cnHor = new Container(BoxLayout.x());

            SpanLabel lbID = new SpanLabel("" + reclamations.get(i).getId());
            Label lbType = new Label(reclamations.get(i).getObjet());
            Label lbDesc = new Label("" + reclamations.get(i).getDescription());
            Label lbstate = new Label("" + reclamations.get(i).getState());
            Label lbsalle = new Label("" + reclamations.get(i).getSalleName());

//            Reclamation get = reclamations.get(i);
//            add(addReclamation(get));

            cnVer.add(lbID);
            cnVer.add(lbType);
            cnVer.add(lbDesc);
            cnVer.add(lbstate);
            cnVer.add(lbsalle);
            add(cnVer);
        }


    }

//    private Container addReclamation(Reclamation r){
//        Container holder = new Container(BoxLayout.x());
//        Container detaills = new Container(BoxLayout.y());
//
//
//
//
//        Label id=new Label ("id:"+r.getId());
//        id.setTextPosition(RIGHT);
//        Label categ=new Label ("objet:"+r.getObjet());
//        categ.setTextPosition(RIGHT);
//        Label des =new Label ("description:"+r.getDescription());
//        des.setTextPosition(RIGHT);
//
//        Button btnSupprimer = new Button("");
//        Style supprimerStyle=new Style(btnSupprimer.getUnselectedStyle());
//        supprimerStyle.setFgColor(0xf21f1f);
//        FontImage supprimerImage=FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerStyle);
//        btnSupprimer.setIcon(supprimerImage);
//        btnSupprimer.addPointerPressedListener(l-> {
//
//
//            if(ReclamationService.getInstance().deleteReclamation(r.getId())){
//                new ReclamationListe().show();
//
//            }
//
//        });
//        Button btnModifier = new Button("");
//        Style modifierStyle=new Style(btnModifier.getUnselectedStyle());
//        modifierStyle.setFgColor(0xf7ad02);
//        FontImage modifierImage=FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
//        btnModifier.setIcon(modifierImage);
//
//
//        btnModifier.addPointerPressedListener(l->{
//
////            new modi(r).show();
//
//
//
//        });
//        detaills.add(id);
//        detaills.add(categ);
//
//        detaills.add(des);
//
//        detaills.add(btnSupprimer);
//        detaills.add(btnModifier);
//
//
//
//
//
//        holder.add(detaills);
//        return (holder);
//
//    }



}

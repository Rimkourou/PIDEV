package tn.esprit.TuniShow.gui;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.TuniShow.entity.Reclamation;
import tn.esprit.TuniShow.services.ReclamationService;

public class AddReclamation extends Form {
    public AddReclamation() {

        setTitle("Add a new Reclamation");
        setLayout(BoxLayout.y());

        TextField tfobjet = new TextField("", "objet");
        TextField tfDescription = new TextField("", "description");
        TextField tfSalle = new TextField("", "salleName");
        Button btnValider = new Button("Add Reclamation");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfDescription.getText().length() == 0) || (tfobjet.getText().length() == 0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else {
                    try {
                        Reclamation r = new Reclamation(tfobjet.getText(), tfDescription.getText(), "pas encore", 1, 7);
                        if (ReclamationService.getInstance().addReclamation(r)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                            new ReclamationListe().show();
                        }
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", String.valueOf(new Command("OK")));
                    }

                }


            }
        });

        addAll(tfobjet, tfDescription, tfSalle, btnValider);
//        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
//                , e -> previous.showBack()); // Revenir vers l'interface précédente

    }
}

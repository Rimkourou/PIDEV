package tn.esprit.TuniShow.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.TuniShow.entity.Film;
import tn.esprit.TuniShow.entity.Reservation;
import tn.esprit.TuniShow.services.ReservationService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class addReservationForm extends Form {
    public addReservationForm(Form previous){
        setTitle("book a ticket");
        setLayout(BoxLayout.y());
        //TextField datedereservation = new TextField("","yyyy-MM-dd");
        TextField iduser = new TextField("","Enter your name");
        TextField idsalle = new TextField("","Enter a movie room");
        TextField idfilm = new TextField("","enter a movie");
        TextField nbrplaceres = new TextField("","enter number of places");
        Button btnValider = new Button("Add task");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (((iduser.getText().length()==0)||(idsalle.getText().length()==0)||(idfilm.getText().length()==0)||(nbrplaceres.getText().length()==0)))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        InfiniteProgress ip = new InfiniteProgress(); //loading after insert data
                        //final Dialog iDialog = ip.showInfiniteBlocking();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Reservation r = new Reservation(format.format(new Date()),String.valueOf(iduser.getText()).toString(),
                                String.valueOf(idsalle.getText()).toString(),String.valueOf(idfilm.getText()).toString(),Integer.parseInt(nbrplaceres.getText()));
                        if( ReservationService.getInstance().addReservation(r))
                        {
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        refreshTheme();
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }


            }
        });

        addAll(iduser,idsalle,idfilm,nbrplaceres,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                , e-> previous.showBack()); // Revenir vers l'interface précédente

    }
}

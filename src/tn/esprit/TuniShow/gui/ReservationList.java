package tn.esprit.TuniShow.gui;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import tn.esprit.TuniShow.entity.Reservation;
import tn.esprit.TuniShow.services.ReservationService;

import java.util.ArrayList;
import java.util.Collections;

public class ReservationList extends Form {
    Form current;
    ReservationService rs = new ReservationService();
    ArrayList<Reservation> ResArrayList = new ArrayList<>();
    public ReservationList(Form previous) {
        current = this;
        setTitle("my list");
        setLayout(BoxLayout.y());
        ResArrayList = rs.getAllReservation();
        showResList();
        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        getToolbar().addSearchCommand(e -> {
            String text = (String) e.getSource();
            if (text == null || text.length() == 0) {
                // clear search
                for (Component cmp : getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                getContentPane().animateLayout(150);
            } else {
                text = text.toLowerCase();
                for (Component cmp : getContentPane()) {
                    MultiButton mb = (MultiButton) cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                            line2 != null && line2.toLowerCase().indexOf(text) > -1;
                    mb.setHidden(!show);
                    mb.setVisible(show);

                }
                getContentPane().animateLayout(150);

            }
        }, 4);
        removeAll();
        Collections.sort(ResArrayList, Reservation.MovieNameComparator);
        showResList();
    }

    public void showResList(){
        for (Reservation r : ResArrayList) {
            MultiButton mb = new MultiButton();
            mb.setTextLine1(r.getIdsalle()+" : "+r.getIdfilm());
            mb.setTextLine2(String.valueOf(r.getNbrplaceres())+" "+"places");
            //mb.setTextLine3(r.getDatedereservation().toString());
            add(mb);
        }
        //add(list);
    }

}

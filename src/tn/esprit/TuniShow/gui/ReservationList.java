package tn.esprit.TuniShow.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.util.Resources;
import tn.esprit.TuniShow.services.ReservationService;

public class ReservationList extends Form {

    public ReservationList(Resources res) {
        setTitle("Res List");

        SpanLabel sp = new SpanLabel();
        sp.setText(ReservationService.getInstance().getAllReservation().toString());
        add(sp);
        //getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        Form current=this;
    }
}

package tn.esprit.TuniShow.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.TuniShow.entity.Film;
import tn.esprit.TuniShow.services.FilmService;

import java.io.IOException;

public class FilmDetails extends Form {
    Form current;
    FilmService fs = new FilmService();
    Image img = null;
    ImageViewer imageViewer = null;
    EncodedImage ec;
    public FilmDetails(Form previous, Film f) {
        current = this;
        setTitle("Movie Details");
        setLayout(BoxLayout.y());
        setScrollableY(true);
        Label titleLabel = new Label("Title : " + f.getTitre());
        Label authorLabel = new Label("Author : " + f.getAuteur());
        Label typeLabel = new Label("Type : " + f.getGenre());
        Label categoryLabel = new Label("Category : " + f.getCategorie());
        SpanLabel descriptionLabel = new SpanLabel("Description : " + f.getDescription());

        String url = "http://127.0.0.1:8000/dist/img/"+f.getImg();
        int deviceWidth = Display.getInstance().getDisplayWidth();
        Image placeholder = Image.createImage(deviceWidth-350, deviceWidth-150, 0xbfc9d2);
        EncodedImage encImage = EncodedImage.createFromImage(placeholder, false);
        ec = EncodedImage.createFromImage(placeholder,false);
        img = URLImage.createToStorage(ec, url, url,URLImage.RESIZE_SCALE_TO_FILL);
        imageViewer = new ImageViewer(img);

        addAll(imageViewer, titleLabel, typeLabel, categoryLabel, authorLabel, descriptionLabel);

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
}

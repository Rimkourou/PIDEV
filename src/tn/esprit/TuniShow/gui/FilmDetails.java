package tn.esprit.TuniShow.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import tn.esprit.TuniShow.entity.Film;
import tn.esprit.TuniShow.services.FilmService;

import java.io.IOException;
import java.io.OutputStream;

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

        ShareButton sb = new ShareButton();
        sb.setText("Share Screenshot");

        Image screenshot = Image.createImage(current.getWidth(), current.getHeight());
        current.revalidate();
        current.setVisible(true);
        current.paintComponent(screenshot.getGraphics(), true);

        String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
        try(OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
            ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
        } catch(IOException err) {
            Log.e(err);
        }
        sb.setImageToShare(imageFile, "image/png");

        addAll(imageViewer, titleLabel, typeLabel, categoryLabel, authorLabel, descriptionLabel, sb);

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
}

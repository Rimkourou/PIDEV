/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Entity.Commande;
import Entity.Produit;
import Service.ServiceCommande;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.color.PDGamma;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


/**
 * FXML Controller class
 *
 * @author Malvin
 */

public class AfficherFactureController implements Initializable {

    @FXML
    private AnchorPane chercher;
    @FXML
    private TableView<Produit> table;
    @FXML
    private TableColumn<Produit, String> nom;
    @FXML
    private TableColumn<Produit, String> prix;
    @FXML
    private TableColumn<Produit, String> Qte;
    @FXML
    private TextField idClient;
    @FXML
    private TextField montant;
    @FXML
    private TextField montant1;
    @FXML
    private TextField email;
    @FXML
    private TextField idCommande;
    @FXML
    private TextField date;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<Produit, ImageView> image;

    /**
     * Initializes the controller class.
     */
    public void setResNom(TextField resNom) {
        this.idClient.setText(resNom.getText());
    }
     public void setDate(String resNom) {
        this.date.setText(resNom);
    }
    public void setEmail(String resNom) {
        this.email.setText(resNom);
    }
     public void setTotal(String resNom) {
        this.montant.setText(resNom);
    }
      public void setTotal2(String resNom) {
        this.montant1.setText(resNom);
    }
      public void setCommande(String resNom) {
        this.idCommande.setText(resNom);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
   
            ServiceCommande sc =new ServiceCommande();
               
            ObservableList<Produit> listu  = FXCollections.observableArrayList(); 
            Commande C = new Commande();
            C.setId(sc.getLast());
            try {
                        
                for(Produit bb: sc.getProduitCommande(C))
                {
                    Image i = new Image(bb.getImage().substring(56));
                ImageView im = new ImageView() ;
               im.setFitHeight(100);
im.setFitWidth(100);
                im.setImage(i);
               bb.setIm(im);
                                   listu.add(bb);

                }
            } catch (SQLException ex) {
                Logger.getLogger(AfficherFactureController.class.getName()).log(Level.SEVERE, null, ex);
            }
            nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
                        id.setCellValueFactory(new PropertyValueFactory<>("id"));
            image.setCellValueFactory(new PropertyValueFactory<>("im"));
            prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            Qte.setCellValueFactory(new PropertyValueFactory<>("Qte"));
            
            table.setItems(listu);
        } catch (SQLException ex) {
            Logger.getLogger(AfficherFactureController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void Telecharger(ActionEvent event) {
try {
    Node stag = ((Node) event.getSource());
    stag.setVisible(false);
    
    stag.managedProperty().bind(stag.visibleProperty());
    Scene scene = table.getScene(); 
    WritableImage nodeshot =
            new WritableImage(600, 400);
    scene.snapshot(nodeshot);
    
    //   WritableImage nodeshot =  stage.snapshot(new SnapshotParameters(), null);
    
    File file = new File("chart.png");
    
    try {
        ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
    } catch (IOException e) {
        
    }
    
    PDDocument doc    = new PDDocument();
    // PDPage page = new PDPage();
    PDPage page = new PDPage(new PDRectangle(1000,800));
    PDImageXObject pdimage;
    PDPageContentStream content;
    pdimage = PDImageXObject.createFromFile("chart.png",doc);
    content = new PDPageContentStream(doc, page);
    content.setNonStrokingColor(Color.decode("#f4f4f4"));
content.addRect(0, 0,1000,1000 );
content.fill();
    content.drawImage(pdimage, 100,100);

    content.close();
    doc.addPage(page);
    doc.save("Facture.pdf");
    doc.close();
    file.delete();
    
    
} catch (IOException ex) {
            Logger.getLogger(AfficherFactureController.class.getName()).log(Level.SEVERE, null, ex);

}
           

        }
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master_login2;

import Services.PromotionService;
import entities.Promotion;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FrontPromotion implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane promotionPane;

    @FXML
    private Button btn_promotion;
    @FXML
    private Button signOut;
    
    @FXML
    void lier(ActionEvent event) throws IOException {
        if(event.getSource() == btn_promotion){
            Parent fxml = FXMLLoader.load(getClass().getResource("FXML.fxml"));
            promotionPane.getChildren().removeAll();
            promotionPane.getChildren().setAll(fxml);
        }
    }
    @FXML
    void handleButtonAction(ActionEvent event) {
        
        if(event.getSource()==signOut){
            Parent view5;
            try {
            view5= FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            
            Scene scene5=new Scene(view5);
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene5);
            window.show();
             }
             catch (IOException ex) {
                Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PromotionService fs=new PromotionService();
        ObservableList<Promotion> list= fs.read();
        
        //list=utils.Mysqlconnect.getDataPromotion();
        gridPane.setHgap(40);
        gridPane.setVgap(0);

        int col = 0;
        int row = 0;

        Button[] movieBtn = new Button[list.size()];

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for(int i = 0; i < list.size(); i++){
            //------------------
            
            String Datee=list.get(i).getDateD().toString();
            String Datee2=list.get(i).getDateF().toString();
            Label[] labell=new Label[list.size()]; 
            
            labell[i]=new Label("Start Date/End Date:\n"+Datee+"/"+Datee2);

            labell[i].setPadding(new Insets(270,0,-15,0));
            labell[i].setFont(new Font("Arial", 14));
             
            gridPane.add(labell[i], col, row);
            //-----------------------
            
            
            String Redu=list.get(i).getReduction();
            Label[] labell2=new Label[list.size()]; 
            
            labell2[i]=new Label("Reduction:"+Redu);

            labell2[i].setPadding(new Insets(350,0,-15,0));
            labell2[i].setFont(new Font("Arial", 16));
            
             
            gridPane.add(labell2[i], col, row);

            //-----------------------
            
            String urrl = list.get(i).getCastPhoto();

            Image image = new Image("file:C:\\Users\\ASUS\\Desktop\\master_Login22\\master_Login2\\src\\Data\\"+urrl);
            ImageView[] imageView=new ImageView[list.size()]; 
            imageView[i] = new ImageView(image);
            
            imageView[i].setFitWidth(180d);
            imageView[i].setFitHeight(250d);
          
            gridPane.add(imageView[i], col, row);
            col ++;

            if(col == 6){
                row ++;
                col = 0;}}
    }    
    
}

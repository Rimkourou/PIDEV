
package controller.ons;


import entitie.ons.Promotion;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import service.ons.PromotionService;
import utils.SingletonConnection;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PromotionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    PromotionService us=new PromotionService();
 

     @FXML
    private TextField filterField;

    
    @FXML
    private TableView<Promotion> table_promotion;


    @FXML
    private TableColumn<Promotion, Integer> col_idP;
    @FXML
    private TableColumn<Promotion, String> col_critere;

    @FXML
    private TableColumn<Promotion, Date> col_DateD;

    @FXML
    private TableColumn<Promotion, Date> col_DateF;
    @FXML
    private TableColumn<Promotion, String> col_reduction ;
     @FXML
    private TableColumn<Promotion, Integer> col_id_user;
     
    @FXML
    private DatePicker  txt_dateD;
    @FXML
    private TextField txt_reduction;
    @FXML
    private DatePicker  txt_dateF;
      @FXML
    private TextField txt_critere;
      @FXML
    private TextField txt_id1;
    String picName;
    @FXML
    private ImageView imageid;
    @FXML
    private HBox user_vBox;
    @FXML
    private HBox promotion_vBox;

    @FXML
    private HBox movie_vBox;

    @FXML
    private HBox complaint_vBox;
    @FXML
    private AnchorPane page;
    @FXML
    private ComboBox my_account;
    
    
    ObservableList<Promotion> listP;
    ObservableList<Promotion> dataList;
    
    int index= -1;
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    @FXML
    void handleButtonAction(ActionEvent event) {
        
        if(event.getSource()==my_account){
            Parent view5;
            try {
            view5= FXMLLoader.load(getClass().getResource("../../GUI/ons/FXMLDocument.fxml"));
            
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
    
    @FXML
    void lier(MouseEvent event) throws IOException {
            if(event.getSource() == promotion_vBox){
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/ons/Promotion.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);
           }
            else if(event.getSource() ==user_vBox){
            Parent fxml = FXMLLoader.load(getClass().getResource("../../GUI/ons/Admin.fxml"));
            page.getChildren().removeAll();
            page.getChildren().setAll(fxml);
            }
    }
     
    
    public void Add_Promotions() {
        try {
            int idUser= FXMLDocumentController.currentUser;
        Promotion p = new Promotion(txt_critere.getText(),Date.valueOf(txt_dateD.getValue()),Date.valueOf(txt_dateF.getValue()),idUser,picName,txt_reduction.getText());
        PromotionService sp = new PromotionService();
     
        sp.Add_Promotions(p);
        update_tableP();  
        search();
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
    public void update_tableP(){
        ObservableList<Promotion> listP= us.read();
        col_idP.setCellValueFactory(new PropertyValueFactory<Promotion,Integer>("id"));
        col_critere.setCellValueFactory(new PropertyValueFactory<Promotion,String>("critaire"));
        col_DateD.setCellValueFactory(new PropertyValueFactory<Promotion,Date>("dateD"));
        col_DateF.setCellValueFactory(new PropertyValueFactory<Promotion,Date>("dateF"));
        col_id_user.setCellValueFactory(new PropertyValueFactory<Promotion,Integer>("idUser"));
        col_reduction.setCellValueFactory(new PropertyValueFactory<Promotion,String>("reduction"));
        table_promotion.setItems(listP);   
    }
    public void getSelected(){
        
        index =table_promotion.getSelectionModel().getSelectedIndex();
        
        if(index<=-1){
            
            return;
        }
        txt_id1.setText(col_idP.getCellData(index).toString());
        txt_critere.setText(col_critere.getCellData(index).toString());
        txt_dateD.setValue(col_DateD.getCellData(index).toLocalDate());
        //setValue(new Date(p.getDate().getTime()).toLocalDate());
        txt_dateF.setValue(col_DateF.getCellData(index).toLocalDate());
        txt_reduction.setText(col_reduction.getCellData(index).toString());
        
    }
    public void Editer(){
        try {
            conn = SingletonConnection.getInstance().getCnx();
            Promotion p = new Promotion(Integer.parseInt(txt_id1.getText()),txt_critere.getText()/*Date.valueOf(txt_dateD.getText()),Date.valueOf(txt_dateF.getText())*/,Date.valueOf(txt_dateD.getValue()),Date.valueOf(txt_dateF.getValue()),txt_reduction.getText());
            PromotionService sp = new PromotionService();
            sp.EditP(p);
            update_tableP();
         JOptionPane.showMessageDialog(null, "modifié avec succé");
         pst.execute();
         search();
        } catch (Exception e) {
           // JOptionPane.showMessageDialog(null, e);
           // Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public void Supprimer(){
        try {
            int id =Integer.parseInt(txt_id1.getText());
            PromotionService sp = new PromotionService();
            sp.DeleteP(id);
            update_tableP();
            JOptionPane.showMessageDialog(null, "Promotion deleted successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
     @FXML
    void search() {          
        col_idP.setCellValueFactory(new PropertyValueFactory<Promotion,Integer>("id"));
        col_critere.setCellValueFactory(new PropertyValueFactory<Promotion,String>("critaire"));
        col_DateD.setCellValueFactory(new PropertyValueFactory<Promotion,Date>("dateD"));
        col_DateF.setCellValueFactory(new PropertyValueFactory<Promotion,Date>("dateF"));
        col_id_user.setCellValueFactory(new PropertyValueFactory<Promotion,Integer>("idUser"));
        
        dataList =  us.getDataPromotion();
        table_promotion.setItems(dataList);
        FilteredList<Promotion> filteredData = new FilteredList<>(dataList, b -> true); 
        
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(person -> {
        if (newValue == null || newValue.isEmpty()) {
        return true;
        }    
        String lowerCaseFilter = newValue.toLowerCase();
    
        if (person.getCritaire().toLowerCase().indexOf(lowerCaseFilter) != -1 )
        {
        return true; 
        }                            
        else  
              return false; // Does not match.
   });
  });  
  SortedList<Promotion> sortedData = new SortedList<>(filteredData);  
  sortedData.comparatorProperty().bind(table_promotion.comparatorProperty());  
  table_promotion.setItems(sortedData);      
    }
    
    
    public void setOnAction() throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = fileChooser.showOpenDialog(null);
        String path=file.getAbsolutePath();
       
        picName = path.substring(path.lastIndexOf(File.separator)+1);
//---------------
//        int pos = path.lastIndexOf("/") + 1;
//        picName=path.substring(pos, path.length()-pos);
 //--------------       
       //picName=file.getAbsolutePath();
       

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageid.setImage(image);
        } catch (IOException e) {
        
        }
    }
   
         public void printBillOnAction() {

        try {
            InputStream is = this.getClass().getResourceAsStream("/report/voiceePromotion.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(is);
            HashMap<String, Object> hs = new HashMap<>();
            //hs.put("ID", col_id.getText());
            hs.put("ID", col_idP.getText());
            hs.put("critere", col_critere.getText());
            hs.put("dateD", col_DateD.getText());
            hs.put("dateF",col_DateF.getText());
            hs.put("idAdmin",col_id_user.getText());
        
            JasperPrint jp = JasperFillManager.fillReport(jr, hs,utils.SingletonConnection.getInstance().getCnx());
            JasperViewer.viewReport(jp);

        } catch (JRException e) {
            e.printStackTrace();
        }


    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      update_tableP();
      search();
      my_account.getItems().addAll("log out");
      
    }

    public void handleShowRooms(MouseEvent mouseEvent) throws IOException {

        Parent movieParent = FXMLLoader.load(getClass().getResource("../../GUI/hazem/admin/SalledeCinemaAdmin.fxml"));
        Scene movieScene = new Scene(movieParent, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(movieScene);
        window.show();


    }

    public void handleShowComplaint(MouseEvent mouseEvent) throws IOException {

        Parent complaintParent = FXMLLoader.load(getClass().getResource("../../GUI/hazem/admin/ReclamationAdmin.fxml"));
        Scene complaintScene = new Scene(complaintParent, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(complaintScene);
        window.show();
    }

    public void handleShowFilm(MouseEvent mouseEvent) throws IOException {
        Parent Parent3 = FXMLLoader.load(getClass().getResource("../../GUI/wifek/DashboardFilm.fxml"));
        Scene Scene3 = new Scene(Parent3, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(Scene3);
        window.show();
    }

    public void handleShowReservation(MouseEvent mouseEvent) throws IOException {
        Parent Parent4 = FXMLLoader.load(getClass().getResource("../../GUI/wifek/DashboardReservation.fxml"));
        Scene Scene4 = new Scene(Parent4, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(Scene4);
        window.show();
    }

    public void handleShowPlanning(MouseEvent mouseEvent) throws IOException {
        Parent Parent5 = FXMLLoader.load(getClass().getResource("../../GUI/sana/Planning.fxml"));
        Scene Scene5 = new Scene(Parent5, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(Scene5);
        window.show();
    }

    public void handleShowSpectacle(MouseEvent mouseEvent) throws IOException {
        Parent Parent6 = FXMLLoader.load(getClass().getResource("../../GUI/sana/Spectacle.fxml"));
        Scene Scene6 = new Scene(Parent6, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(Scene6);
        window.show();
    }
}

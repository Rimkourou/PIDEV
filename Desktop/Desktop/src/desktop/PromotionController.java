/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;

import Services.PromotionService;
import Services.UserService;
import entites.Promotion;
import entites.User;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import utils.Mysqlconnect;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
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
    private TableColumn<Promotion, Integer> col_id_user;
    @FXML
    private TextField txt_dateD;

    @FXML
    private TextField txt_dateF;
      @FXML
    private TextField txt_critere;
      @FXML
    private TextField txt_id1;


    
    
    ObservableList<Promotion> listP;
    ObservableList<Promotion> dataList;
    
    int index= -1;
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    
    public void Add_Promotions() {
        try {
        Promotion p = new Promotion(txt_critere.getText(),Date.valueOf( txt_dateD.getText()), Date.valueOf(txt_dateF.getText()));
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
        
        table_promotion.setItems(listP);   
    }
    public void getSelected(){
        
        index =table_promotion.getSelectionModel().getSelectedIndex();
        
        if(index<=-1){
            
            return;
        }
        txt_id1.setText(col_idP.getCellData(index).toString());
        txt_critere.setText(col_critere.getCellData(index).toString());
        txt_dateD.setText(col_DateD.getCellData(index).toString());
        txt_dateF.setText(col_DateF.getCellData(index).toString());
       
    }
    public void Editer(){
        try {
            conn = Mysqlconnect.getInstance().getCnx();
            Promotion p = new Promotion(Integer.parseInt(txt_id1.getText()),txt_critere.getText(), Date.valueOf(txt_dateD.getText()),Date.valueOf(txt_dateF.getText()));
            PromotionService sp = new PromotionService();
            sp.EditP(p);
            update_tableP();
         JOptionPane.showMessageDialog(null, "modifié avec succé");
         pst.execute();
         search();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, e);
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
        
        
        dataList =  Mysqlconnect.getDataPromotion();
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      update_tableP();
      search();
    }    
    
}

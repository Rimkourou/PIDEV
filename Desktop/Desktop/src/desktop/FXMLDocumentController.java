/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop;

import entites.Promotion;
import entites.User;
import Services.UserService;
import java.net.URL;
import utils.Mysqlconnect;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
;
import java.sql.ResultSet;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import sun.security.util.Length;
import utils.Mysqlconnect;

/**
 *
 * @author ASUS
 */
public class FXMLDocumentController implements Initializable {
    UserService us = new UserService();
    
   
    @FXML
    private TableView<User> table_users;

    @FXML
    private TableColumn<User, Integer> col_id;

    @FXML
    private TableColumn<User, String> col_cin;

    @FXML
    private TableColumn<User, String> col_nom;

    @FXML
    private TableColumn<User, String> col_prenom;

    @FXML
    private TableColumn<User, String> col_role;
    @FXML
    private TableColumn<User, String> col_email;

    @FXML
    private TableColumn<User, String> col_password;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_role;
      @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_cin;

    @FXML
    private TextField txt_prenom;
     @FXML
    private TextField filterField;

    
    
    
    ObservableList<User> listM;
    ObservableList<User> dataList;
    int index= -1;
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;

      
    public void Ajouter_users() {
        try {
        User u = new User(txt_cin.getText(), txt_nom.getText(), txt_prenom.getText(),txt_role.getText(),txt_email.getText(),txt_password.getText());
        UserService sp = new UserService();
        sp.Add_users(u); 
        update_table();
            
            //search_user();
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
    
    public void getSelected(){
        
        index =table_users.getSelectionModel().getSelectedIndex();
        
        if(index<=-1){
            
            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_cin.setText(col_cin.getCellData(index));
        txt_nom.setText(col_nom.getCellData(index));
        txt_prenom.setText(col_prenom.getCellData(index));
        txt_role.setText(col_role.getCellData(index));
        txt_email.setText(col_email.getCellData(index));
        txt_password.setText(col_password.getCellData(index));
       
    }
       public void Editer(){
        try {
            conn = Mysqlconnect.getInstance().getCnx();
            User u = new User(Integer.parseInt(txt_id.getText()),txt_cin.getText(), txt_nom.getText(), txt_prenom.getText(),txt_role.getText(),txt_email.getText(),txt_password.getText());
            UserService sp = new UserService();
            sp.Edit(u);
            update_table();
            
//            String value1 =txt_id.getText();
//            String value2 =txt_cin.getText();
//            String value3 =txt_nom.getText();
//            String value4 =txt_prenom.getText();
//            String value5 =txt_role.getText();
//            String value6 =txt_email.getText();
//            String value7 =txt_password.getText();
//            
//         String sql ="update user set id= '"+value1+"' ,  cin= '"+value2+"' , nom= '"+value3+"' , prenom= '"+value4+"' , role= '"+value5+"', email= '"+value6+"' ,password= '"+value7+"' where id='"+value1+"'";
         //pst=conn.prepareStatement(sql);
         JOptionPane.showMessageDialog(null, "modifié avec succé");
         pst.execute();
         search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
       
       public void Supprimer(){
        try {
            int id =Integer.parseInt(txt_id.getText());
            UserService sp = new UserService();
            sp.Delete(id);
            update_table();
            JOptionPane.showMessageDialog(null, "User deleted successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
       public void update_table(){
        ObservableList<User> listM= us.read();
        col_id.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        col_cin.setCellValueFactory(new PropertyValueFactory<User,String>("cin"));
        col_nom.setCellValueFactory(new PropertyValueFactory<User,String>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<User,String>("prenom"));
        col_role.setCellValueFactory(new PropertyValueFactory<User,String>("role"));
        col_email.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
        col_password.setCellValueFactory(new PropertyValueFactory<User,String>("password"));

        //listM =Mysqlconnect.getDataUsers();
        table_users.setItems(listM);   
       
    }
       @FXML
    void search_user() {          
        col_id.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        col_cin.setCellValueFactory(new PropertyValueFactory<User,String>("cin"));
        col_nom.setCellValueFactory(new PropertyValueFactory<User,String>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<User,String>("prenom"));
        col_role.setCellValueFactory(new PropertyValueFactory<User,String>("role"));
        col_email.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
        col_password.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
        
        dataList =  Mysqlconnect.getDataUsers();
        table_users.setItems(dataList);
        FilteredList<User> filteredData = new FilteredList<>(dataList, b -> true);  
 filterField.textProperty().addListener((observable, oldValue, newValue) -> {
 filteredData.setPredicate(person -> {
    if (newValue == null || newValue.isEmpty()) {
     return true;
    }    
    String lowerCaseFilter = newValue.toLowerCase();
    
    if (person.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 )
    {
     return true; // Filter matches username
//    } else if (person.getPassword().toLowerCase().indexOf(lowerCaseFilter) != -1) 
//    {
//     return true; // Filter matches password
//    }
    }
    else if (person.getRole().toLowerCase().indexOf(lowerCaseFilter) != -1)
    {
     return true; // Filter matches role
    }
    else if (person.getCin().toLowerCase().indexOf(lowerCaseFilter) != -1)
    {
     return true; // Filter matches cin
    }
    else if (String.valueOf(person.getEmail()).indexOf(lowerCaseFilter)!=-1)
         return true;// Filter matches email
                                
         else  
          return false; // Does not match.
   });
  });  
  SortedList<User> sortedData = new SortedList<>(filteredData);  
  sortedData.comparatorProperty().bind(table_users.comparatorProperty());  
  table_users.setItems(sortedData);      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       update_table();
       search_user();
    }  
       
}

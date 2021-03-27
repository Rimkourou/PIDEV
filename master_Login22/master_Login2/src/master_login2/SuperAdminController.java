/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master_login2;

import entities.User;
import Services.UserService;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import utils.Mysqlconncet;

/**
 *
 * @author ASUS
 */
public class SuperAdminController implements Initializable {
    UserService us = new UserService();
    
   
    @FXML
    private TableView<User> table_users;

    @FXML
    private TableColumn<User, Integer> col_id;

    @FXML
    private TableColumn<User, String> col_state;

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
    private TableColumn<User, String> col_question;
    @FXML
    private TableColumn<User, String> col_answer;
    @FXML
    private TableColumn<User, Date> col_age;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_email;

    @FXML
    private ComboBox txt_role;
    @FXML
    private DatePicker txt_age;
      @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_prenom;
     @FXML
    private TextField filterField;
     @FXML
    private ComboBox state_user;
     @FXML
    private ComboBox question;
     @FXML
    private TextField txt_Answer;
     
    
    
    ObservableList<User> listM2;
    ObservableList<User> dataList;
    int index= -1;
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
   
    ObservableList<String> questionBoxList = FXCollections.observableArrayList("What's your pet's name?","What's your favorite food?","Who was your childhood hero?");

   public void Ajouter_users() {
        
        try {
  
        User u = new User(txt_nom.getText(),txt_prenom.getText(),Date.valueOf(txt_age.getValue()),txt_role.getValue().toString(),txt_email.getText(),txt_password.getText(),state_user.getValue().toString(),question.getValue().toString(),txt_Answer.getText());
        UserService sp = new UserService();
        sp.Add_users(u);
        update_table();
            
        search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            Logger.getLogger(SuperAdminController.class.getName()).log(Level.SEVERE, null, e);
        }
       
        
    }
    
    public void getSelected(){
        
        index =table_users.getSelectionModel().getSelectedIndex();
        
        if(index<=-1){
            
            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_nom.setText(col_nom.getCellData(index));
        txt_prenom.setText(col_prenom.getCellData(index));
        txt_age.setValue(col_age.getCellData(index).toLocalDate());
        //txt_role.setText(col_role.getCellData(index));
        txt_email.setText(col_email.getCellData(index));
        txt_password.setText(col_password.getCellData(index));
        txt_Answer.setText(col_answer.getCellData(index));
       
    }
       public void Editer(){
        try {
            conn = Mysqlconncet.getInstance().getCnx();
            User u;
            u = new User(Integer.parseInt(txt_id.getText()),
                    txt_nom.getText(),
                    txt_prenom.getText(),
                    Date.valueOf(txt_age.getValue()),
                    txt_role.getValue().toString(),
                    txt_email.getText(),
                    txt_password.getText(),
                    state_user.getValue().toString(),
                    question.getValue().toString(),
                    txt_Answer.getText());
            UserService sp = new UserService();
            sp.Edit(u);
            update_table();
            
//            String value1 =txt_id.getText();
//            String value2 =txt_nom.getText();
//            String value3 =txt_prenom.getText();
//            String value4 =txt_age.getText();
//            String value5 =txt_role.getText();
//            String value6 =txt_email.getText();
//            String value7 =txt_password.getText();
//            String value8 =state_user.getValue().toString();
//            String value9 =question.getValue().toString();
//            String value10 =txt_Answer.getText();
//            
//         String sql ="update user set id= '"+value1+"' ,nom= '"+value2+"' , prenom= '"+value3+"',age= '"+value4+"' , role= '"+value5+"', email= '"+value6+"' ,password= '"+value7+"',state= '"+value8+"',s_ques= '"+value9+"',answer= '"+value10+"' where id='"+value1+"'";
//         pst=conn.prepareStatement(sql);
          //  JOptionPane.showMessageDialog(null, "modifié avec succé");
//         pst.execute();
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
        ObservableList<User> listM2;
        listM2 = us.readAdmin();
        col_id.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<User,String>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<User,String>("prenom"));
        col_age.setCellValueFactory(new PropertyValueFactory<User,Date>("age"));
        col_role.setCellValueFactory(new PropertyValueFactory<User,String>("role"));
        col_email.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
        col_password.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
        col_state.setCellValueFactory(new PropertyValueFactory<User,String>("state"));
        col_question.setCellValueFactory(new PropertyValueFactory<User,String>("s_ques"));
        col_answer.setCellValueFactory(new PropertyValueFactory<User,String>("answer"));

        //listM =Mysqlconnect.getDataUsers();
        table_users.setItems(listM2);  
       
    }
       public void printBillOnAction() {

        try {
            InputStream is = this.getClass().getResourceAsStream("/report/invoicee.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(is);
            HashMap<String, Object> hs = new HashMap<>();
            //hs.put("ID", col_id.getText());
            hs.put("Name", col_nom.getText());
            hs.put("LastName", col_prenom.getText());
            hs.put("Role", col_role.getText());
            hs.put("Email",col_email.getText());
            hs.put("State",col_state.getText());
        
            JasperPrint jp = JasperFillManager.fillReport(jr, hs,utils.Mysqlconncet.getInstance().getCnx());
            JasperViewer.viewReport(jp);

        } catch (JRException e) {
            e.printStackTrace();
        }


    }
       @FXML
    void search_user() {          
        col_id.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<User,String>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<User,String>("prenom"));
        col_role.setCellValueFactory(new PropertyValueFactory<User,String>("role"));
        col_email.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
        col_password.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
        col_state.setCellValueFactory(new PropertyValueFactory<User,String>("state"));
        
        dataList =  us.readAdmin();
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
     return true; 
    }
    else if (person.getRole().toLowerCase().indexOf(lowerCaseFilter) != -1)
    {
     return true; // Filter matches role
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
       state_user.getItems().addAll("active","bloqué");
       //question.getItems().addAll("What's your pet's name?","What's your favorite food?","Who was your childhood hero?");
       //type.getItems().addAll("Super administrateur","Admin","Membre");
       txt_role.getItems().addAll("admin");
       //question.setValue("Choose your question");
       question.setItems(questionBoxList);
    }  
       
}

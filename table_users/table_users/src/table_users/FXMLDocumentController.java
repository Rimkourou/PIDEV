/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table_users;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class FXMLDocumentController implements Initializable {
      @FXML
    private AnchorPane pane_user;

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
    private AnchorPane pane_promotion;
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

    @FXML
    private AnchorPane pane_salles;

    @FXML
    private TextField txt_nom11;

    @FXML
    private TextField txt_password11;

    @FXML
    private TextField txt_id11;
    //---------------------------------------------------

    
    ObservableList<User> listM;
    ObservableList<Promotion> listP;
   // ObservableList<Users> dataList;
    int index= -1;
    
    Connection conn=null;
    ResultSet rs=null;
    PreparedStatement pst=null;
    
    
    public void user_paneShow(){
        pane_user.setVisible(true);
        pane_promotion.setVisible(false);
        pane_salles.setVisible(false);
    }
    public void promotion_paneShow(){
        pane_user.setVisible(false);
        pane_promotion.setVisible(true);
        pane_salles.setVisible(false);
    }
    public void salles_paneShow(){
        pane_user.setVisible(false);
        pane_promotion.setVisible(false);
        pane_salles.setVisible(true);
    }
    
    
    
    public void Add_users(){
       conn=mysqlconnect.connectDb();
       String sql ="insert into user(cin,nom,prenom,role,email,password)values(?,?,?,?,?,?)";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_cin.getText());
            pst.setString(2, txt_nom.getText());
            pst.setString(3, txt_prenom.getText());
            pst.setString(4, txt_role.getText());
            pst.setString(5, txt_email.getText());
            pst.setString(6, txt_password.getText());
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "ajout avec succé");
            update_table();
            //JavaMail.sendMail("ons.driss@esprit.tn");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //methode select users
    
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
     public void Edit(){
        try {
            conn=mysqlconnect.connectDb();
            String value1 =txt_id.getText();
            String value2 =txt_cin.getText();
            String value3 =txt_nom.getText();
            String value4 =txt_prenom.getText();
            String value5 =txt_role.getText();
            String value6 =txt_email.getText();
            String value7 =txt_password.getText();
            
            
            String sql ="update user set id= '"+value1+"' ,  cin= '"+value2+"' , nom= '"+value3+"' , prenom= '"+value4+"' , role= '"+value5+"', email= '"+value6+"' ,password= '"+value7+"' where id='"+value1+"'";
         pst=conn.prepareStatement(sql);
         pst.execute();
         JOptionPane.showMessageDialog(null, "modifié avec succé");
         update_table();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
     
     public void Delete(){
    conn = mysqlconnect.connectDb();
    String sql = "delete from user where id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Delete");
            update_table();
            //search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    
    }
    
    
    
   public void update_table(){
        
        col_id.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        col_cin.setCellValueFactory(new PropertyValueFactory<User,String>("cin"));
        col_nom.setCellValueFactory(new PropertyValueFactory<User,String>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<User,String>("prenom"));
        col_role.setCellValueFactory(new PropertyValueFactory<User,String>("role"));
        col_email.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
        col_password.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
        
        
        listM =mysqlconnect.getDataUsers();
        table_users.setItems(listM);   
        
        
    }
     //---------------------------------------------------
   public void Add_Promotions(){
       conn=mysqlconnect.connectDb();
       //String sql = 'SELECT id FROM user WHERE username=\'' . $_SESSION['nom'] . '\'';

       String sql ="insert into promotion(critaire,dateD,dateF,idUser)values(?,?,?,?)";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1, txt_critere.getText());
            pst.setString(2, txt_dateD.getText());
            pst.setString(3, txt_dateF.getText());
            pst.setString(4, "1");
            
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "ajout avec succé");
            update_tableP();
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void update_tableP(){
        
        col_idP.setCellValueFactory(new PropertyValueFactory<Promotion,Integer>("id"));
        col_critere.setCellValueFactory(new PropertyValueFactory<Promotion,String>("critaire"));
        col_DateD.setCellValueFactory(new PropertyValueFactory<Promotion,Date>("dateD"));
        col_DateF.setCellValueFactory(new PropertyValueFactory<Promotion,Date>("dateF"));
        col_id_user.setCellValueFactory(new PropertyValueFactory<Promotion,Integer>("idUser"));
        
        
        listP =mysqlconnect.getDataPromotion();
        table_promotion.setItems(listP);   
    }
    public void getSelectedP(){
        
        index =table_promotion.getSelectionModel().getSelectedIndex();
        
        if(index<=-1){
            
            return;
        }
        txt_id1.setText(col_idP.getCellData(index).toString());
        txt_critere.setText(col_critere.getCellData(index));
        txt_dateD.setText(col_DateD.getCellData(index).toString());
        txt_dateF.setText(col_DateF.getCellData(index).toString());    
    }
    public void EditP(){
        try {
            conn=mysqlconnect.connectDb();
            String value1 =txt_id1.getText();
            String value2 =txt_critere.getText();
            String value3 =txt_dateD.getText();
            String value4 =txt_dateF.getText();
            
            String sql ="update promotion set id= '"+value1+"' ,  critaire= '"+value2+"' , dateD= '"+value3+"' , dateF= '"+value4+"' where id='"+value1+"'";
         pst=conn.prepareStatement(sql);
         pst.execute();
         JOptionPane.showMessageDialog(null, "modifié avec succé");
         update_tableP();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void DeleteP(){
    conn = mysqlconnect.connectDb();
    String sql = "delete from promotion where id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id1.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Delete");
            update_tableP();
            //search_user();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        update_table();
        update_tableP();
        /*col_id.setCellValueFactory(new PropertyValueFactory<Users,Integer>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Users,String>("username"));
        col_password.setCellValueFactory(new PropertyValueFactory<Users,String>("password"));
        col_email.setCellValueFactory(new PropertyValueFactory<Users,String>("email"));
        col_type.setCellValueFactory(new PropertyValueFactory<Users,String>("type"));
        
        listM =mysqlconnect.getDataUsers();
        table_users.setItems(listM);  */
        

    }    
    
}

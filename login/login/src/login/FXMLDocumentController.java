/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane pane_login;

    @FXML
    private TextField txt_username;

    @FXML
    private PasswordField txt_password;
    @FXML
    private ComboBox type;
    @FXML
    private Button btn_login;

    @FXML
    private AnchorPane pane_signup;

    @FXML
    private TextField txt_username_up;

    @FXML
    private TextField txt_password_up;

    @FXML
    private TextField txt_email_up;
    @FXML
    private TextField txt_prenom_up;

    @FXML
    private TextField txt_cin_up;
    
    
    Connection conn = null;
    ResultSet rs =null;
    PreparedStatement pst =null;

    public void Login_paneShow(){
        pane_login.setVisible(true);
        pane_signup.setVisible(false);
    }
    public void Signup_paneShow(){
        pane_login.setVisible(false);
        pane_signup.setVisible(true);
    }
    
    @FXML
    private void Login (ActionEvent event) throws Exception{
        conn=Mysqlconnect.connectDb();
        String sql ="Select * from user where nom = ? and password = ? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_username.getText());
            pst.setString(2, txt_password.getText());
            //pst.setString(3, type.getValue().toString());
            rs=pst.executeQuery();
            
           
            String user="Select role FROM user WHERE nom=['txt_username']";
            
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Username and Password is Correct");
                if(user.equals("membre")){
                btn_login.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("CPanel.fxml"));
                Stage mainStage =new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
                }
                else{
                btn_login.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("CPanel.fxml"));
                Stage mainStage =new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
                }
            
            
            }else
                JOptionPane.showMessageDialog(null, "Invalide Username Or Password");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void add_users (ActionEvent events){
       conn=Mysqlconnect.connectDb();
       String value ="membre";
       String sql ="insert into user (cin,nom,prenom,role,email,password) values(?,?,?,?,?,?)";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,txt_cin_up.getText());
            pst.setString(2,txt_username_up.getText());
            pst.setString(3,txt_prenom_up.getText());
            pst.setString(4,"membre");
            pst.setString(5,txt_email_up.getText());
            pst.setString(6,txt_password_up.getText());
          
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "saved");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //type_up.getItems().addAll("Super administrateur","Admin","Membre");
       //type.getItems().addAll("Super administrateur","Admin","Membre");

    }    
    
}

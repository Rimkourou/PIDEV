/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.pkg2;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
//import javax.mail.Message.RecipientType;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import utils.Mysqlconnect;

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
    private TextField hide;
        @FXML
    private AnchorPane pane_otp;
          @FXML
    private PasswordField enterOTP;
        @FXML
    private Button save;
//      @FXML
//    private Button btn_signup;
//      @FXML
//    private Button login;
//
//    @FXML
//    private Button sign_up;
    
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
    public void Otp(){
        
            Random();
            pane_login.setVisible(false);
            pane_signup.setVisible(false);
            pane_otp.setVisible(true);
            sendotp();
            
    }
    public void save(){
      if(hide.getText().equals(enterOTP.getText())) {
          add_users();
      }
      else{
          JOptionPane.showMessageDialog(null,"wrong otp");
      }
    }

    public FXMLDocumentController() {
        conn=Mysqlconnect.getInstance().getCnx();
    }
    
    
    public void Random()
    {
        Random rd=new Random();
        hide.setText(""+rd.nextInt(100000+1));
    }
    public void sendotp(){
        
        Properties props=new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port",465);
        props.put("mail.smtp.user","tunishow.tn@gmail.com");
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.debug",true);
        props.put("mail.smtp.socketFactory.port",465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback",false); 
        
        try {             
                Session session = Session.getDefaultInstance(props, null);
                session.setDebug(true);
                MimeMessage message = new MimeMessage(session);
                message.setText("Your OTP is " + hide.getText());
                message.setSubject("OTP For your Neftola Account");
                message.setFrom(new InternetAddress("tunishow.tn@gmail.com"));
                message.addRecipient(RecipientType.TO, new InternetAddress(txt_email_up.getText()));
                message.saveChanges();
                try
                {
                Transport transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com","tunishow.tn@gmail.com","tunishow1234");
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                
            txt_email_up.setEditable(true);
            pane_login.setVisible(false);
            pane_signup.setVisible(false);
            pane_otp.setVisible(true);
                 
                JOptionPane.showMessageDialog(null,"OTP has send to your Email id"); 
                }catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"Please check your internet connection");
                }              
            
        } catch (Exception e) {
            e.printStackTrace();  
            JOptionPane.showMessageDialog(null,e);
        }  
    }
    public void add_users (/*ActionEvent events*/){
//      try {
//        User u = new User(txt_username_up.getText(),txt_prenom_up.getText(),"membre",txt_email_up.getText(),txt_password_up.getText());
//        UserService sp = new UserService();
//        sp.Add_users(u);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, e);
//        }
    conn = Mysqlconnect.getInstance().getCnx();
       
       String sql ="insert into user (nom,prenom,role,email,password,state) values(?,?,?,?,?,?)";
       //String email="Select email FROM user";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1,txt_username_up.getText());
            pst.setString(2,txt_prenom_up.getText());
            pst.setString(3,"membre");
            pst.setString(4,txt_email_up.getText());
            pst.setString(5,txt_password_up.getText());
            pst.setString(6,"active");
            
            pst.execute();
             
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    @FXML
    private void Login (ActionEvent event) throws Exception{
        conn=Mysqlconnect.getInstance().getCnx();
//        UserService sp = new UserService();
//        sp.login();
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
//    public void handleButtonAction(ActionEvent event){
//        if(event.getSource()==btn_login)
//        {
////            pane_login.setVisible(true);
////            pane_signup.setVisible(false);
////            
//        }
//        
//        else if(event.getSource()==btn_signup){
//            Random();
//            pane_login.setVisible(false);
//            pane_signup.setVisible(false);
//            pane_otp.setVisible(true);
//            sendotp();
//        }
//    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}

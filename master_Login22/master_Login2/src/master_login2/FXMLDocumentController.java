/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master_login2;

import Services.UserService;
import Services.mailing;
import entities.User;
//import static Services.mailing.sendMail;
import java.awt.SystemTray;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import org.apache.commons.codec.digest.DigestUtils;
import static sun.security.krb5.KrbException.errorMessage;
import utils.Mysqlconncet;

/**
 *
 * @author ASUS
 */
public class FXMLDocumentController implements Initializable {
    
    ObservableList<String> questionBoxList = FXCollections.observableArrayList("What's your pet's name?","What's your favorite food?","Who was your childhood hero?");

    @FXML
    private AnchorPane login1;

    @FXML
    private Label forgot;

    @FXML
    private AnchorPane signup2;

    @FXML
    private Button Enter_signup;

    @FXML
    private AnchorPane login2;
      @FXML
    private AnchorPane otpSystem;

    @FXML
    private Button Enter_signin;

    @FXML
    private AnchorPane signup1;
    @FXML
    private Button close;
     @FXML
    private TextField txt_username_up;

    @FXML
    private TextField txt_prenom_up;

    @FXML
    private TextField txt_email_up;

    @FXML
    private PasswordField txt_password_up;
    
    @FXML
    private PasswordField enterOTP;

    @FXML
    private Button save;

    @FXML
    private TextField hide;

    @FXML
    private Button btn_signup;
    @FXML
    private ComboBox questionBox;

    @FXML
    private TextField txt_answer_up;
    @FXML
    private Label errorMessage;
    @FXML
    private TextField txtemail;
    @FXML
    private DatePicker txt_age;

    @FXML
    private PasswordField txtpass;
    @FXML
    private Button login;
    @FXML
    private Label errorMsg;
    @FXML
    private Label label_message;
    @FXML
    private CheckBox checkbox;  
    @FXML
    private TextField txt_pass;
    @FXML
    private AnchorPane loginPane;
    
   static int currentUser;
         
    
    Connection conn = null;
    ResultSet rs =null;
    PreparedStatement pst =null;
    
    public FXMLDocumentController() {
        conn=Mysqlconncet.getInstance().getCnx();
    }
    public void Random()
    {
        Random rd=new Random();
        hide.setText(""+rd.nextInt(100000+1));
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        if(event.getSource()==Enter_signin)
        {
            login1.setVisible(true);
            login2.setVisible(false);
            signup2.setVisible(true);
            signup1.setVisible(false);
        }
        else if(event.getSource()==Enter_signup)
        {
            login1.setVisible(false);
            login2.setVisible(true);
            signup2.setVisible(false);
            signup1.setVisible(true);
        }
        else if(event.getSource()==btn_signup)
        {
            try {
                conn = Mysqlconncet.getInstance().getCnx();

        
            String nom=txt_username_up.getText().trim();
            String prenom=txt_prenom_up.getText().trim();
            String email=txt_email_up.getText().trim();
            Date Age=Date.valueOf(txt_age.getValue());
            String password=DigestUtils.shaHex(txt_password_up.getText());
            String ques = questionBox.getValue().toString();
            String ans = txt_answer_up.getText().trim();
            
            if(nom.isEmpty() || prenom.isEmpty() || ques.isEmpty() || ans.isEmpty() || email.isEmpty() || password.isEmpty()){
                errorMessage.setText("Please complete all the fills");
            }
            else {
               if (password.length()<6){
                   errorMessage.setText("Password is too weak, please choose atleast 6 characters");
               }
            else {
                   
            String sql = "select * from user where nom = ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1, nom);
            
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                errorMessage.setText("Username already taken, please try another username");
            }
            
            //_________________________________________
            else{
            String sql1 = "select * from user where email = ?";
            pst=conn.prepareStatement(sql1);
            pst.setString(1, email);
            
            ResultSet rs1 = pst.executeQuery();
            if(rs1.next()){
                errorMessage.setText("You already have an account");
            }
            
               
            
            else{
                //--------------------------------------------------
                    Random();
                    signup1.setVisible(false);
                    login2.setVisible(false);
                    otpSystem.setVisible(true);  
                    Services.mailing.sendMail(txt_email_up.getText(), "THANK YOU FOR YOUR INSCRIPTION \n"+"This is YOUR OTP:   "+hide.getText());
                    
                    }
            } }}} catch (Exception ex) {
            }
        }
        else if(event.getSource()==login)
        {
            login(event);
        }
        else if(event.getSource()==save)
        {
          if(hide.getText().equals(enterOTP.getText())) 
          {
          add_users(event);
          label_message.setText("Added successfully");
          
            Parent view5;
            view5= FXMLLoader.load(getClass().getResource("FrontPromotion.fxml"));
            Scene scene5=new Scene(view5);
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene5);
            window.show();
          }
          else{
          JOptionPane.showMessageDialog(null,"wrong otp");
              }
          
        }
        else if(event.getSource()==close)
        {
            System.exit(0);
        }
    }
    public void check(ActionEvent event){
        if(checkbox.isSelected())
        {
            txt_pass.setText(txtpass.getText());
            txt_pass.setVisible(true);
            txtpass.setVisible(false);
            return;
        }
            txtpass.setText(txt_pass.getText());
            txtpass.setVisible(true);
            txt_pass.setVisible(false);
    }
    

    
    public void add_users (ActionEvent events){
    conn = Mysqlconncet.getInstance().getCnx();

        try {
            String sql2 = "insert into user (nom,prenom,age,role,email,password,state,s_ques,answer) values(?,?,?,?,?,?,?,?,?)";     
            pst = conn.prepareStatement(sql2);
//            String role="membre";
//            String stat="active";
//            User u = new User(txt_username_up.getText(),txt_prenom_up.getText(),Integer.parseInt(txt_age.getText()) ,role,txt_email_up.getText(),txt_password_up.getText(),stat,questionBox.getValue().toString(),txt_answer_up.getText());
//                
//            UserService sp = new UserService();
//            sp.Add_users(u); 
           
            pst.setString(1,txt_username_up.getText().trim());
            pst.setString(2,txt_prenom_up.getText().trim());
            pst.setDate(3,Date.valueOf(txt_age.getValue())); 
            pst.setString(4,"membre");
            pst.setString(5,txt_email_up.getText().trim());
            pst.setString(6,txt_password_up.getText().trim());
            pst.setString(7,"active");
            pst.setString(8,questionBox.getValue().toString() );
            pst.setString(9, txt_answer_up.getText().trim());
            
            pst.execute();
            
            errorMessage.setText("Account successfully registered");
        } catch (Exception e) {
        }
    }
    
    //__________________________________________________________________________
    
    
    @FXML
    void login(ActionEvent event) throws IOException {
          conn = Mysqlconncet.getInstance().getCnx();
        try {
            String email = txtemail.getText().trim();
            String password = txtpass.getText().trim();

            if(email.isEmpty() || password.isEmpty()){
               errorMsg.setText("Please insert email and password");
            }
            else
            {
                 UserService us=new UserService();
                 User u = us.getUser(txtemail.getText(),txtpass.getText());
              
                 currentUser = u.getId();
                    if (u.getRole().equals("supeAdmin") && u.getPassword().equals("22521"))
                    {
                        Parent view3=FXMLLoader.load(getClass().getResource("SuperAdmin.fxml"));
                        Scene scene3=new Scene(view3);
                        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(scene3);
                        window.show();
                     }
                    if(u.getRole().equals("admin")){
                        Parent view4=FXMLLoader.load(getClass().getResource("Admin.fxml"));
                        Scene scene4=new Scene(view4);
                        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(scene4);
                        window.show();
                    }
                    if(u.getRole().equals("membre")){
                        Parent view5=FXMLLoader.load(getClass().getResource("FrontPromotion.fxml"));
                        Scene scene5=new Scene(view5);
                        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                        window.setScene(scene5);
                        window.show();
                    }
                else {
                    errorMsg.setText("Invalid credentials. Please try again");
                }
            
             }
        }
        catch(Exception ex){
            System.out.println("error" + ex.toString());
        }
    }
    @FXML
public void forgotPsw(ActionEvent event)throws IOException{
Parent view4=FXMLLoader.load(getClass().getResource("GetPassword.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
}  
  
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        questionBox.setValue("Choose your question");
        questionBox.setItems(questionBoxList);
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master_login2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Mysqlconncet;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class GetPasswordController implements Initializable {

    @FXML
    private Button backBtn;

    @FXML
    private TextField usernametxt;

    @FXML
    private TextField nametxt;

    @FXML
    private TextField questiontxt;

    @FXML
    private TextField answertxt;

    @FXML
    private Button searchBtn;

    @FXML
    private Button getpswBtn;
    
    @FXML
    private Label errorAnswer;
       @FXML
    private Label errorLb;
    @FXML
    private TextField passtxt;
    String ans;
    String pass;
    
    
    
    Connection conn = null;
    ResultSet rs =null;
    PreparedStatement pst =null;
    
    public GetPasswordController() {
        conn=Mysqlconncet.getInstance().getCnx();
    }
    
    @FXML
    void searchPsw(ActionEvent event) throws IOException {
        conn = Mysqlconncet.getInstance().getCnx();
       try {
            
            
            passtxt.setText("");
            answertxt.setText("");
            
            String u_name = usernametxt.getText().trim();
            if(u_name.isEmpty()){
                errorLb.setText("Please insert username");
            }
            else {
                String sql = "select prenom, s_ques, answer, password from user where nom=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, u_name);
                
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    nametxt.setText(rs.getString(1));
                    questiontxt.setText(rs.getString(2));
                    ans = rs.getString(3);
                    pass = rs.getString(4);
                    errorLb.setText("");
                    
                    ps.close();
                    rs.close();
                }
                else {
                    errorLb.setText("Error: Username is incorrect");
                }
                
                
            }
            
        } catch (Exception ex) {
            System.out.println("something wrong" + ex);
        } 
            
    }
    @FXML
    void retrivePsw(ActionEvent event) throws IOException {
        
          if(ans.equals(answertxt.getText().trim())){
            passtxt.setText(pass);
        }
        else {
         errorAnswer.setText("Your answer is wrong. Please try again");
        }
            
    }
    
    @FXML
    void backLogin(ActionEvent event) throws IOException {
       Parent view4=FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
     
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class CPanelController implements Initializable {

    @FXML
    private Button btn_acceuil;

    @FXML
    private Button btn_film;

    @FXML
    private Button btn_spectacle;

    @FXML
    private Button btn_salles;

    @FXML
    private TextField txt_recherche;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.ConnexionBD;

/**
 * FXML Controller class
 *
 * @author trabe
 */
public class StatstiqueController implements Initializable {

    @FXML
    private BarChart<String, Float> stat;
    @FXML
    private Button Retour;

    /**
     * Initializes the controller class.
     */
    java.sql.Connection cnx = ConnexionBD.getInstance().getCnx();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String req =" SELECT nom, prix FROM Produit ORDER BY prix ASC";
        XYChart.Series<String, Float> series = new XYChart.Series<>();
        try {
            Statement st =cnx.createStatement();
            ResultSet rs=st.executeQuery(req);
            while (rs.next()){
                series.getData().add(new XYChart.Data<>(rs.getString(1), rs.getFloat(2)));
            }
           stat.getData().add(series);
        } catch (SQLException ex) {
            Logger.getLogger(StatstiqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void Retour(ActionEvent event) {
        try {
            javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("DashboardProduit.fxml"));
            Scene sceneview = new Scene(tableview);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(sceneview);
            window.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    
    }
    
}

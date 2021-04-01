/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.ons;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.ons.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChartController implements Initializable {

    @FXML
    private PieChart pieChart;
    @FXML
    private AnchorPane page;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserService us=new UserService();
        int total=us.Totale();
        int userS=us.countUsersS();
        int userI=us.countUsersI();
        
        float a=(userS*100)/total;
        float m=(userI*100)/total;
        
        ObservableList<PieChart.Data> pieChartData=FXCollections.observableArrayList(
                new PieChart.Data("Age>18",a),
                new PieChart.Data("Age<18",m));
        pieChart.setData(pieChartData);
        
    }
    public void handleBack(MouseEvent mouseEvent) throws IOException {
        Parent Parent2 = FXMLLoader.load(getClass().getResource("../../GUI/ons/Admin.fxml"));
        Scene Scene2 = new Scene(Parent2, 1200, 680);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(Scene2);
        window.show();

    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master_login2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import Services.UserService;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ComputerT
 */
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
    
}

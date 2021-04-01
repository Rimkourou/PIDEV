package test.sana;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardPlanningMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../GUI/sana/Spectacle.fxml"));

        Scene scene = new Scene(root);
//        stage.initStyle(StageStyle.TRANSPARENT);
//        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tuni Show");
        primaryStage.setMaximized(true);
        primaryStage.show();

    }

    public static void main(String[] args){ launch(args);}
}
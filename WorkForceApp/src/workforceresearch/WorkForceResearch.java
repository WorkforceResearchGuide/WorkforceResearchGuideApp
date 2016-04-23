/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workforceresearch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Mark
 */
public class WorkForceResearch extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        AppHandler appHandler = new AppHandler();
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/workforceresearch/mainScreen.fxml"));
        
        Parent root = fxmlLoader.load();
        MainScreenController controller = fxmlLoader.<MainScreenController>getController();
        Scene scene = new Scene(root);
        stage.setTitle("Workforce Research App");
        controller.setAppHandler(appHandler);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

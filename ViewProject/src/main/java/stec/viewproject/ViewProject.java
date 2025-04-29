/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package stec.viewproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jroga
 */
public class ViewProject extends Application {
    
    @Override
    public void start(Stage primary) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primary.setScene(scene);
        primary.setTitle("Sudoku Game");
        primary.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

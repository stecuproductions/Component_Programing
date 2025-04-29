/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package stec.viewproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import stec.model.Difficulty;
import stec.model.SudokuBoard;
import stec.solver.BacktrackingSudokuSolver;
import stec.solver.SudokuSolver;

/**
 * FXML Controller class
 *
 * @author jroga
 */
public class MainViewController implements Initializable {
    @FXML
    private RadioButton easyButton;
    @FXML
    private RadioButton mediumButton;
    @FXML
    private RadioButton hardButton;
    @FXML
    private Button startButton;
    @FXML
    private ToggleGroup level;
    
    @FXML
    private void StartClick() {
        Toggle selected = level.getSelectedToggle();
        if(selected != null) {
            RadioButton selectedLvl = (RadioButton) selected;
            String lvlText = selectedLvl.getText();
            Difficulty difficulty = switch(lvlText.toLowerCase()) {
                case "easy" -> Difficulty.EASY;
                case "medium" -> Difficulty.MEDIUM;
                case "hard" -> Difficulty.HARD;
                default -> throw new IllegalStateException("Uknown level");
            };
            try {
                SudokuSolver solver = new BacktrackingSudokuSolver();
                SudokuBoard prototype = new SudokuBoard(solver);
                prototype.solveGame();
                SudokuBoard solvedBoard = prototype.clone();
                SudokuBoard board = prototype.clone();
                board.removeCells(difficulty.getToRemove());
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameView.fxml"));
                Parent gameRoot = loader.load();
                GameViewController controller = loader.getController();
                controller.setBoard(board, solvedBoard);
                
                Stage stage = (Stage) startButton.getScene().getWindow();
                stage.setScene(new Scene(gameRoot));
                stage.show();
            }
            catch (CloneNotSupportedException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Something went wrong while starting the game.");
                alert.showAndWait();
            }
            catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Unable to load the game view");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a difficulty level before starting.");
            alert.showAndWait();
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        easyButton.setSelected(true);
    }    
    
}

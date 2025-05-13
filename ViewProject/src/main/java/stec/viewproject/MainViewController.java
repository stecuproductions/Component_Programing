/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package stec.viewproject;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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
    private static final Logger logger = LoggerFactory.getLogger(MainViewController.class);
    
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
    private AnchorPane rootPane;
    @FXML
    private MenuItem english;
    @FXML
    private MenuItem polish;
    @FXML
    private Menu languageMenu;
    @FXML
    private Text text1;
    @FXML
    private Text text2;
    @FXML
    private Menu about;
    @FXML
    private MenuItem author;
    
    private Locale currentLocale = new Locale("en");
    
    @FXML
    private void handleEnglishSelected(ActionEvent event) {
        switchLanguage(new Locale("en"));
    }
    
    @FXML
    private void handlePolishSelected(ActionEvent event) {
        switchLanguage(new Locale("pl", "PL"));
    }
      @FXML
    private void StartClick() {
        logger.info("Start button clicked");
        Toggle selected = level.getSelectedToggle();
        if(selected != null) {
            RadioButton selectedLvl = (RadioButton) selected;
            String lvlText = selectedLvl.getText();
            logger.debug("Selected difficulty level: {}", lvlText);
            Difficulty difficulty = switch(lvlText.toLowerCase()) {
                case "easy" -> Difficulty.EASY;
                case "medium" -> Difficulty.MEDIUM;
                case "hard" -> Difficulty.HARD;
                case "łatwy" -> Difficulty.EASY;
                case "średni" -> Difficulty.MEDIUM;
                case "trudny" -> Difficulty.HARD;                    
                default -> throw new IllegalStateException("Uknown level");
            };
            try {
                SudokuSolver solver = new BacktrackingSudokuSolver();
                SudokuBoard prototype = new SudokuBoard(solver);
                prototype.solveGame();
                SudokuBoard solvedBoard = prototype.clone();
                SudokuBoard board = prototype.clone();
                board.removeCells(difficulty.getToRemove());
                
                ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle", currentLocale);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameView.fxml"), bundle);
                Parent gameRoot = loader.load();
                GameViewController controller = loader.getController();
                controller.setBoard(board, solvedBoard);
                controller.setLocale(currentLocale);
                
                Stage stage = (Stage) startButton.getScene().getWindow();
                stage.setScene(new Scene(gameRoot));                stage.show();
                logger.info("Game view initialized and displayed");
            }
            catch (CloneNotSupportedException e) {
                logger.error("Clone error while starting game", e);
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Something went wrong while starting the game.");
                alert.showAndWait();
            }            catch (IOException e) {
                logger.error("IO error while loading game view", e);
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Unable to load the game view");
                alert.showAndWait();
            }
        }        else {
            logger.warn("No difficulty level selected");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select a difficulty level before starting.");
            alert.showAndWait();
        }
    }
    /**
     * Initializes the controller class.
     */    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info("Initializing MainViewController");
        easyButton.setSelected(true);
        ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle", currentLocale);
        updateUI(bundle);
        logger.debug("MainViewController initialized with locale: {}", currentLocale);
    }

    private void switchLanguage(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle", locale);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"), bundle);
            Parent root = loader.load();
            MainViewController controller = loader.getController();
            controller.setLocale(locale);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setTitle(bundle.getString("app.title"));
            stage.setScene(new Scene(root));
        }
        catch (IOException e) {
            logger.error("Failed to change language", e);
        }
    }

    void updateUI(ResourceBundle bundle) {
        startButton.setText(bundle.getString("menu.newGame"));
        easyButton.setText(bundle.getString("menu.easy"));
        mediumButton.setText(bundle.getString("menu.medium"));
        hardButton.setText(bundle.getString("menu.hard"));
        english.setText(bundle.getString("language.english"));
        polish.setText(bundle.getString("language.polish"));
        languageMenu.setText(bundle.getString("menu.language"));
        text1.setText(bundle.getString("start.text1"));
        text2.setText(bundle.getString("start.text2"));
        about.setText(bundle.getString("menu.about"));
        author.setText(bundle.getString("menu.authors"));

    }

    private void setLocale(Locale locale) {
        this.currentLocale = locale;
        ResourceBundle bundle = ResourceBundle.getBundle("MessageBundle", locale);
        updateUI(bundle);
    }
    
   @FXML
   private void handleAbout(ActionEvent event) {
        ResourceBundle authorBundle = ResourceBundle.getBundle("stec.viewproject.Authors", currentLocale);
        String authorText = authorBundle.getString("authors");
        String alertTitle = authorBundle.getString("about.title");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alertTitle);
        alert.setHeaderText(null);
        alert.setContentText(authorText);
        alert.showAndWait();
    }
    
    
}

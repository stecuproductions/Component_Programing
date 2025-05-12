package stec.viewproject;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import stec.dao.Dao;
import stec.dao.DaoException;
import stec.dao.SudokuBoardDaoFactory;

import stec.model.SudokuBoard;
import stec.model.SudokuField;
import stec.solver.BacktrackingSudokuSolver;
import stec.solver.SudokuSolver;

/**
 * FXML Controller class
 *
 * @author jroga
 */
public class GameViewController implements Initializable {
    private Dao<SudokuBoard> sudokuBoardDao;
    @FXML
    private TextField[][] cells;
    @FXML private TextField c00;
    @FXML private TextField c01;
    @FXML private TextField c02;
    @FXML private TextField c03;
    @FXML private TextField c04;
    @FXML private TextField c05;
    @FXML private TextField c06;
    @FXML private TextField c07;
    @FXML private TextField c08;
    @FXML private TextField c10;
    @FXML private TextField c11;
    @FXML private TextField c12;
    @FXML private TextField c13;
    @FXML private TextField c14;
    @FXML private TextField c15;
    @FXML private TextField c16;
    @FXML private TextField c17;
    @FXML private TextField c18;
    @FXML private TextField c20;
    @FXML private TextField c21;
    @FXML private TextField c22;
    @FXML private TextField c23;
    @FXML private TextField c24;
    @FXML private TextField c25;
    @FXML private TextField c26;
    @FXML private TextField c27;
    @FXML private TextField c28;
    @FXML private TextField c30;
    @FXML private TextField c31;
    @FXML private TextField c32;
    @FXML private TextField c33;
    @FXML private TextField c34;
    @FXML private TextField c35;
    @FXML private TextField c36;
    @FXML private TextField c37;
    @FXML private TextField c38;
    @FXML private TextField c40;
    @FXML private TextField c41;
    @FXML private TextField c42;
    @FXML private TextField c43;
    @FXML private TextField c44;
    @FXML private TextField c45;
    @FXML private TextField c46;
    @FXML private TextField c47;
    @FXML private TextField c48;
    @FXML private TextField c50;
    @FXML private TextField c51;
    @FXML private TextField c52;
    @FXML private TextField c53;
    @FXML private TextField c54;
    @FXML private TextField c55;
    @FXML private TextField c56;
    @FXML private TextField c57;
    @FXML private TextField c58;
    @FXML private TextField c60;
    @FXML private TextField c61;
    @FXML private TextField c62;
    @FXML private TextField c63;
    @FXML private TextField c64;
    @FXML private TextField c65;
    @FXML private TextField c66;
    @FXML private TextField c67;
    @FXML private TextField c68;
    @FXML private TextField c70;
    @FXML private TextField c71;
    @FXML private TextField c72;
    @FXML private TextField c73;
    @FXML private TextField c74;
    @FXML private TextField c75;
    @FXML private TextField c76;
    @FXML private TextField c77;
    @FXML private TextField c78;
    @FXML private TextField c80;
    @FXML private TextField c81;
    @FXML private TextField c82;
    @FXML private TextField c83;
    @FXML private TextField c84;
    @FXML private TextField c85;
    @FXML private TextField c86;
    @FXML private TextField c87;
    @FXML private TextField c88;

    
    private SudokuBoard unsolved;
    private SudokuBoard solved;
    
    public void setBoard(SudokuBoard unsolved, SudokuBoard solved) {
        this.unsolved = unsolved;
        this.solved = solved;
        updateBoardView();
    }
    
    private void updateBoardView() {
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                int field = unsolved.get(row, col);
                TextField cell = cells[row][col];
                try {
                    SudokuField sudokuField = unsolved.getField(row, col);
                    JavaBeanIntegerProperty prop = JavaBeanIntegerPropertyBuilder.create()
                            .bean(sudokuField)
                            .name("fieldValue")
                            .build();
                    TextFormatter<Integer> formatter = new TextFormatter<>(
                            new SudokuFieldConverter(),
                            sudokuField.getFieldValue(),
                            change -> {
                                String newText = change.getControlNewText();
                                return newText.matches("[1-9]?") ? change : null;
                            }
                    );
                    Bindings.bindBidirectional(formatter.valueProperty(), prop.asObject());
                    cell.setTextFormatter(formatter);
                    cell.setEditable(sudokuField.getFieldValue() == 0);
                }
                catch (NoSuchMethodException e) {
                    System.out.println("no such method");
                }
            }
        }
    }
    
    @FXML
    private void cellEdit(ActionEvent event) {
        TextField cell = (TextField) event.getSource();
        int row = -1;
        int col = -1;
        outer:
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j] == cell) {
                    row = i;
                    col = j;
                    break outer;
                }
            }
        }
        if (row == -1 || col == -1) {
            return;
        }
        String text = cell.getText();
        try {
            int value = Integer.parseInt(text);
            if(value >= 1 && value <=9) {
                if(value != solved.get(row, col)) {
                    cell.setText("");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mistake");
                    alert.setContentText("Incorrect value, please try again.");
                    alert.showAndWait();
                }
                if(isBoardComplete()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Congratulations!");
                    alert.setContentText("You solved the puzzle!");
                    alert.showAndWait();
                }
                if(value == solved.get(row, col)) {
                    unsolved.set(row, col, value);
                    String currentStyle = cell.getStyle();
                    cell.setStyle(currentStyle + "-fx-text-fill: green;");
                    cell.setEditable(false);
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid input");
                alert.setContentText("Please enter a value between 1 and 9");
                alert.showAndWait();
                cell.setText("");
                cell.setText("");
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid input");
            alert.setContentText("Please enter a valid integer.");
            alert.showAndWait();
            cell.setText("");
        }
    }
    
    private boolean isBoardComplete() {
        for(int row = 0; row < 9; row++) {
            for(int col = 0; col < 9; col++) {
                if(unsolved.get(row, col) != solved.get(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @FXML
    private void handleSavePuzzle(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save puzzle");
        dialog.setHeaderText("Enter a name for the puzzle file:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            try {
                sudokuBoardDao.write(name, unsolved);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Puzzle saved successfully!");
                alert.showAndWait();                
            }
            catch (DaoException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to save the puzzle.");
                alert.showAndWait();
            }
        });
    }
    
    @FXML
    private void handleLoadPuzzle(ActionEvent event) {
        List<String> puzzleNames = sudokuBoardDao.names();
         if (puzzleNames.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No available puzzles to load");
                alert.showAndWait();            
                return;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(puzzleNames.get(0), puzzleNames);
        dialog.setTitle("Load Puzzle");
        dialog.setHeaderText("Choose a puzzle to load:");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
                try {
                    SudokuSolver solver = new BacktrackingSudokuSolver();
                    SudokuBoard loaded = sudokuBoardDao.read(name);
                    unsolved = loaded;
                    updateBoardView();
                    SudokuBoard temp = new SudokuBoard(solver);
                    for(int row = 0; row < 9; row++) {
                        for(int col = 0; col < 9; col++) {
                            temp.set(row,col,loaded.get(row, col));
                        }
                    }
                    temp.solveGame();
                    solved = temp;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Puzzle loaded successfully!");
                    alert.showAndWait();
                }
                catch (DaoException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Failed to load the puzzle.");
                    alert.showAndWait();
                }
        });
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cells = new TextField[][] {
        { c00, c01, c02, c03, c04, c05, c06, c07, c08 },
        { c10, c11, c12, c13, c14, c15, c16, c17, c18 },
        { c20, c21, c22, c23, c24, c25, c26, c27, c28 },
        { c30, c31, c32, c33, c34, c35, c36, c37, c38 },
        { c40, c41, c42, c43, c44, c45, c46, c47, c48 },
        { c50, c51, c52, c53, c54, c55, c56, c57, c58 },
        { c60, c61, c62, c63, c64, c65, c66, c67, c68 },
        { c70, c71, c72, c73, c74, c75, c76, c77, c78 },
        { c80, c81, c82, c83, c84, c85, c86, c87, c88 }
    };
        sudokuBoardDao = new SudokuBoardDaoFactory("C:\\Users\\jroga\\Desktop\\studia\\IV\\CP\\puzzles").getFileDao();
    }    
    
}

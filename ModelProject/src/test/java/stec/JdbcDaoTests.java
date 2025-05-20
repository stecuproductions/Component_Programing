package stec;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stec.dao.Dao;
import stec.dao.JdbcSudokuBoardDao;
import stec.dao.SudokuBoardDaoFactory;
import stec.model.SudokuBoard;
import stec.solver.BacktrackingSudokuSolver;
import stec.solver.SudokuSolver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class JdbcDaoTests {
    private static  Connection testConnection;
    @BeforeAll
    static void init() {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");
        try{
            testConnection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void readWriteSudokuBoardDaoTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        board.solveGame();
        Dao<SudokuBoard> dao = factory.getDatabaseDao();
        dao.write("Test board", board);
        System.out.println(board.get(0,0));
        SudokuBoard readBoard = dao.read("Test board");
        assertTrue(readBoard.equals(board));
    }
    @Test
    public void NamesSudokuBoardDaoTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        board.solveGame();
        Dao<SudokuBoard> dao = factory.getDatabaseDao();
        dao.write("Test board", board);
        List<String> names = dao.names();
        assertTrue(names.contains("Test board"));
    }

    @AfterEach
    public void ClearTestRecords() {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        try (Connection testConnection = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = testConnection.prepareStatement(
                     "DELETE FROM SudokuBoard WHERE name ILIKE ?")) {

            testConnection.setAutoCommit(false);
            ps.setString(1, "%Test%"); // dopasowuje wszystko co zawiera "Test"
            ps.executeUpdate();
            testConnection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

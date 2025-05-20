package stec.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import stec.exceptions.SudokuDaoConnectionException;
import stec.exceptions.SudokuDaoNamesException;
import stec.exceptions.SudokuDaoReadException;
import stec.exceptions.SudokuDaoWriteException;
import stec.model.SudokuBoard;
import stec.solver.BacktrackingSudokuSolver;
import stec.solver.SudokuSolver;


public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private final Connection connection;

    public JdbcSudokuBoardDao(String url, String user, String password) throws SudokuDaoConnectionException {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SudokuDaoConnectionException("Problem connecting to PostgreSQL database", e);
        }
    }


    @Override
    public SudokuBoard read(String name) throws SudokuDaoReadException {
        try {
            int boardId;
            String selectBoard = "SELECT id FROM sudokuboard WHERE name = ?";
            try (PreparedStatement ps = connection.prepareStatement(selectBoard)) {
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    boardId = rs.getInt("id");
                } else {
                    throw new SudokuDaoReadException("Failed to read a board");
                }
            }
            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
            String selectFields =  "SELECT value, row, col  FROM SudokuField WHERE board_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(selectFields)) {
                ps.setInt(1, boardId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int value = rs.getInt("value");
                    int row = rs.getInt("row");
                    int col = rs.getInt("col");
                    board.set(row, col, value);
                }
                return board;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SudokuDaoReadException("Problem reading sudoku board", e);
        }
    }

    @Override
    public void write(String name, SudokuBoard board) throws SudokuDaoWriteException {
        try {
            connection.setAutoCommit(false);
            int boardId;
            String insertBoard = "INSERT INTO SudokuBoard(name) VALUES (?) "
                    + "ON CONFLICT (name) DO UPDATE SET name = EXCLUDED.name " + "RETURNING id";
            try (PreparedStatement ps = connection.prepareStatement(insertBoard)) {
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    boardId = rs.getInt(1);
                } else {
                    throw new SudokuDaoWriteException("Failed to write sudoku board");
                }
            }

            String insertField = """
                INSERT INTO SudokuField(board_id, value, row, col)
                VALUES (?, ?, ?, ?)
                ON CONFLICT (board_id, row, col)
                DO UPDATE SET value = EXCLUDED.value
            """;
            try (PreparedStatement ps = connection.prepareStatement(insertField)) {
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        ps.setInt(1, boardId);
                        ps.setInt(2, board.get(row, col));
                        ps.setInt(3, row);
                        ps.setInt(4, col);
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }
            connection.commit();
        } catch (SQLException e) {
            throw new SudokuDaoWriteException("Problem setting up connection", e);
        }
    }

    @Override
    public List<String> names() throws SudokuDaoNamesException {
        List<String> result = new ArrayList<>();
        String sql = "SELECT name FROM SudokuBoard ORDER BY name";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new SudokuDaoNamesException("Cannot fetch board names", e);
        }

        return result;
    }


    @Override
    public void close() {

    }
}

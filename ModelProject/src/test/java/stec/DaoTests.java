package stec;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import stec.dao.*;
import stec.exceptions.SudokuDaoException;
import stec.model.SudokuBoard;
import stec.solver.BacktrackingSudokuSolver;
import stec.solver.SudokuSolver;

public class DaoTests {
  @Test
  void testGetFileDaoCreatesValidDao() throws Exception {
    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory("testFactoryDir");
    Dao<SudokuBoard> dao = factory.getFileDao();
    SudokuSolver solver = new BacktrackingSudokuSolver();
    assertNotNull(dao);
    SudokuBoard board = new SudokuBoard(solver);
    dao.write("factoryGame", board);
    SudokuBoard loaded = dao.read("factoryGame");
    assertEquals(board, loaded);
  }

  @Test
  void createDirectoriesThrowsExceptionDao() {
    String pathToFile = "nonExistentFile.txt";
    new java.io.File(pathToFile).delete();
    try {
      new java.io.File(pathToFile).createNewFile();
    } catch (java.io.IOException e) {
      fail("IOException when trying to create file");
    }
    RuntimeException thrown =
        assertThrows(
            RuntimeException.class,
            () -> {
              new FileSudokuBoardDao(pathToFile + "/subdir"); // tworzymy folder w  pliku
            });
    assertTrue(thrown.getMessage().contains("Failed to create directory:"));
  }

  @Test
  void namesTest() throws SudokuDaoException {
    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory("testFactoryDir");
    Dao<SudokuBoard> dao = factory.getFileDao();
    SudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard board = new SudokuBoard(solver);
    board.solveGame();
    SudokuBoard board2 = new SudokuBoard(solver);
    board2.solveGame();
    SudokuBoard board3 = new SudokuBoard(solver);
    board3.solveGame();
    dao.write("board1", board);
    dao.write("board2", board2);
    dao.write("board3", board3);
    List<String> names = dao.names();
    assertEquals("board1", names.get(0));
    assertEquals("board2", names.get(1));
    assertEquals("board3", names.get(2));
  }

  @Test
  void nameTestsShouldThrowRuntimeExcepotion() throws Exception {

    FileSudokuBoardDao dao = new FileSudokuBoardDao("validDir");
    Path fakeDir = Paths.get("notADir");
    Files.deleteIfExists(fakeDir);
    Files.createFile(fakeDir);
    var field = FileSudokuBoardDao.class.getDeclaredField("directory");
    field.setAccessible(true);
    field.set(dao, fakeDir);
    assertThrows(RuntimeException.class, dao::names);
    Files.deleteIfExists(fakeDir);
  }

  @Test
  void readTestShouldThrowDaoException() throws SudokuDaoException {
    FileSudokuBoardDao dao = new FileSudokuBoardDao("ValidDir");
    assertThrows(SudokuDaoException.class, () -> dao.read("fakeDir"));
  }

  @Test
  void writeTestShouldThrowDaoException() throws SudokuDaoException, Exception {
    FileSudokuBoardDao dao = new FileSudokuBoardDao("ValidDir");
    Path fakeDir = Path.of("notADir");
    Files.deleteIfExists(fakeDir);
    Files.createFile(fakeDir);
    var field = FileSudokuBoardDao.class.getDeclaredField("directory");
    field.setAccessible(true);
    field.set(dao, fakeDir);
    SudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard board = new SudokuBoard(solver);
    board.solveGame();
    assertThrows(SudokuDaoException.class, () -> dao.write("board1", board));
    Files.deleteIfExists(fakeDir);
  }

  @Test
  void closeTestJustForCoverage() {
    assertDoesNotThrow(
        () -> {
          try (FileSudokuBoardDao dao = new FileSudokuBoardDao("testDir")) {}
        });
  }

  @AfterEach
  void cleanup() throws IOException {
    Path dir = Paths.get("testFactoryDir");
    if (Files.exists(dir) && Files.isDirectory(dir)) {
      try (var stream = Files.walk(dir)) {
        stream
            .sorted((a, b) -> -a.compareTo(b))
            .forEach(
                path -> {
                  try {
                    Files.deleteIfExists(path);
                  } catch (IOException e) {
                    System.err.println("Nie mogę usunąć: " + path);
                  }
                });
      }
    }

    Files.deleteIfExists(Paths.get("ValidDir/fakeDir.ser"));
    Files.deleteIfExists(Paths.get("ValidDir"));
    Files.deleteIfExists(Paths.get("notADir"));
    Files.deleteIfExists(Paths.get("nonExistentFile.txt"));
    Files.deleteIfExists(Paths.get("testFile.txt"));
  }
}

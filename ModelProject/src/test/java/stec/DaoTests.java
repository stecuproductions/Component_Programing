package stec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import stec.dao.Dao;
import stec.dao.FileSudokuBoardDao;
import stec.dao.SudokuBoardDaoFactory;
import stec.exceptions.SudokuDaoException;
import stec.model.SudokuBoard;
import stec.solver.BacktrackingSudokuSolver;
import stec.solver.SudokuSolver;

public class DaoTests {  @Test
  void testGetFileDaoCreatesValidDao() throws Exception {
    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory("testFactoryDir");
    SudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard board = new SudokuBoard(solver);
    
    try (Dao<SudokuBoard> dao = factory.getFileDao()) {
      assertNotNull(dao);
      dao.write("factoryGame", board);
      SudokuBoard loaded = dao.read("factoryGame");
      assertEquals(board, loaded);
    }
  }  @Test
  void createDirectoriesThrowsExceptionDao() {
    String pathToFile = "nonExistentFile.txt";
    new java.io.File(pathToFile).delete();
    try {
      new java.io.File(pathToFile).createNewFile();
    } catch (java.io.IOException e) {
      fail("IOException when trying to create file");
    }
    
  
    assertThrows(
        stec.exceptions.SudokuDaoDirectoryException.class,
        () -> {
          try (FileSudokuBoardDao dao = new FileSudokuBoardDao(pathToFile + "/subdir")) {
            // This should never execute as the constructor should throw an exception
            fail("Should have thrown an exception");
          }
        });
  }
  @Test
  void namesTest() throws SudokuDaoException {
    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory("testFactoryDir");
    SudokuSolver solver = new BacktrackingSudokuSolver();
    
    SudokuBoard board = new SudokuBoard(solver);
    board.solveGame();
    SudokuBoard board2 = new SudokuBoard(solver);
    board2.solveGame();
    SudokuBoard board3 = new SudokuBoard(solver);
    board3.solveGame();
    
    try (Dao<SudokuBoard> dao = factory.getFileDao()) {
      dao.write("board1", board);
      dao.write("board2", board2);
      dao.write("board3", board3);
      List<String> names = dao.names();
      assertTrue(names.contains("board1"));
      assertTrue(names.contains("board2"));
      assertTrue(names.contains("board3"));
      assertEquals(3, names.size());
    }
  }
  @Test
  void nameTestsShouldThrowDaoNamesException() throws Exception {
    try (FileSudokuBoardDao dao = new FileSudokuBoardDao("validDir")) {
      Path fakeDir = Paths.get("notADir");
      Files.deleteIfExists(fakeDir);
      Files.createFile(fakeDir);
      
      var field = FileSudokuBoardDao.class.getDeclaredField("directory");
      field.setAccessible(true);
      field.set(dao, fakeDir);
      
      // Should throw SudokuDaoNamesException instead of RuntimeException
      assertThrows(stec.exceptions.SudokuDaoNamesException.class, dao::names);
      Files.deleteIfExists(fakeDir);
    }
  }
  @Test
  void readTestShouldThrowDaoReadException() {
    try (FileSudokuBoardDao dao = new FileSudokuBoardDao("ValidDir")) {
      // Should throw the specific SudokuDaoReadException instead of general SudokuDaoException
      assertThrows(stec.exceptions.SudokuDaoReadException.class, () -> dao.read("fakeDir"));
    }
  }
  @Test
  void writeTestShouldThrowDaoWriteException() throws Exception {
    try (FileSudokuBoardDao dao = new FileSudokuBoardDao("ValidDir")) {
      Path fakeDir = Path.of("notADir");
      Files.deleteIfExists(fakeDir);
      Files.createFile(fakeDir);
      
      var field = FileSudokuBoardDao.class.getDeclaredField("directory");
      field.setAccessible(true);
      field.set(dao, fakeDir);
      
      SudokuSolver solver = new BacktrackingSudokuSolver();
      SudokuBoard board = new SudokuBoard(solver);
      board.solveGame();
      
      assertThrows(stec.exceptions.SudokuDaoWriteException.class, () -> dao.write("board1", board));
      Files.deleteIfExists(fakeDir);
    }
  }
  @Test
  void testAutoCloseableFunctionality() {
    assertDoesNotThrow(
        () -> {
          try (FileSudokuBoardDao dao = new FileSudokuBoardDao("testDir")) {
          }
        });
  }
  
  @Test
  void testResourcesAreClosedAfterOperations() {
    SudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard testBoard = new SudokuBoard(solver);
    testBoard.solveGame();
    
    assertDoesNotThrow(() -> {
      try (Dao<SudokuBoard> dao = new SudokuBoardDaoFactory("testAutoCloseDir").getFileDao()) {
        dao.write("autoCloseTest", testBoard);
      }
    });
    
    assertDoesNotThrow(() -> {
      try (Dao<SudokuBoard> dao = new SudokuBoardDaoFactory("testAutoCloseDir").getFileDao()) {
        SudokuBoard loadedBoard = dao.read("autoCloseTest");
        assertEquals(testBoard, loadedBoard);
      }
    });
    
    assertDoesNotThrow(() -> {
      try (Dao<SudokuBoard> dao = new SudokuBoardDaoFactory("testAutoCloseDir").getFileDao()) {
        List<String> names = dao.names();
        assertTrue(names.contains("autoCloseTest"));
      }
    });
  }  @AfterEach
  void cleanup() throws IOException {
    cleanDirectory(Paths.get("testFactoryDir"));
    cleanDirectory(Paths.get("testDir"));
    cleanDirectory(Paths.get("testAutoCloseDir"));
    
    Files.deleteIfExists(Paths.get("ValidDir/fakeDir.ser"));
    Files.deleteIfExists(Paths.get("ValidDir"));
    Files.deleteIfExists(Paths.get("notADir"));
    Files.deleteIfExists(Paths.get("nonExistentFile.txt"));
    Files.deleteIfExists(Paths.get("testFile.txt"));
  }
  

  private void cleanDirectory(Path dir) throws IOException {
    if (Files.exists(dir) && Files.isDirectory(dir)) {
      try (var stream = Files.walk(dir)) {
        stream
            .sorted((a, b) -> -a.compareTo(b))
            .forEach(
                path -> {
                  try {
                    Files.deleteIfExists(path);
                  } catch (IOException e) {
                    System.err.println("Cannot delete: " + path);
                  }
                });
      }
    }
  }
}

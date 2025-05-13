package stec.dao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stec.exceptions.SudokuDaoDirectoryException;
import stec.exceptions.SudokuDaoException;
import stec.exceptions.SudokuDaoNamesException;
import stec.exceptions.SudokuDaoReadException;
import stec.exceptions.SudokuDaoWriteException;
import stec.model.SudokuBoard;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
  private final Path directory;
  private static final Logger logger = LoggerFactory.getLogger(FileSudokuBoardDao.class.getName());


  public FileSudokuBoardDao(String dirName) {
    this.directory = Paths.get(dirName);
    try {
      logger.debug("creating sudoku board directory");
      Files.createDirectories(this.directory);
    } catch (IOException e) {
      logger.error("Failed to create directory: {}", e.getMessage());
      throw new SudokuDaoDirectoryException("sudoku.exception.dao.directory", e);
    }
  }

  @Override
  public void write(String name, SudokuBoard board) throws SudokuDaoException {
    logger.debug("writing sudoku board");
    Path file = directory.resolve(name + ".ser");
    
    try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
      oos.writeObject(board);
    } catch (IOException e) {
      logger.error("Failed to write SudokuBoard: {}", e.getMessage());
      throw new SudokuDaoWriteException("sudoku.exception.dao.write", e);
    }
  }

 
  @Override
  public SudokuBoard read(String name) throws SudokuDaoException {
    logger.debug("reading sudoku board");
    Path file = directory.resolve(name + ".ser");
    
    try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
      return (SudokuBoard) ois.readObject();
    } catch (IOException e) {
      logger.error("Failed to read SudokuBoard: {}", e.getMessage());
      throw new SudokuDaoReadException("sudoku.exception.dao.read", e);
    } catch (ClassNotFoundException e) {
      logger.error("Failed to deserialize SudokuBoard: {}", e.getMessage());
      throw new SudokuDaoReadException("sudoku.exception.deserialization", e);
    }
  }

  public List<String> names() {
    List<String> result = new ArrayList<>();
    
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.ser")) {
      logger.info("listing sudoku boards");
      for (Path file : stream) {
        result.add(file.getFileName().toString().replace(".ser", ""));
      }
    } catch (IOException e) {
      logger.error("Failed to list SudokuBoard names: {}", e.getMessage());
      throw new SudokuDaoNamesException("sudoku.exception.dao.names", e);
    }
    return result;
  }

  @Override
  public void close() {
    logger.debug("FileSudokuBoardDao closed");
  }
}

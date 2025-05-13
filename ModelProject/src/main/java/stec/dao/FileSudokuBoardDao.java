package stec.dao;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stec.exceptions.SudokuDaoException;
import stec.model.SudokuBoard;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
  private final Path directory;
  private static final Logger logger = LoggerFactory.getLogger(FileSudokuBoardDao.class.getName());

  public FileSudokuBoardDao(String dirName) {
    this.directory = Paths.get(dirName);
    try {
      logger.debug("creating sudoku board");
      Files.createDirectories(this.directory);
    } catch (IOException e) {
      logger.error(e.getMessage());
      throw new RuntimeException("Failed to create directory: " + e);
    }
  }

  @Override
  public void write(String name, SudokuBoard board) throws SudokuDaoException {
    logger.debug("writing sudoku board");
    Path file = directory.resolve(name + ".ser");
    try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
      oos.writeObject(board);
    } catch (IOException e) {
      logger.error(e.getMessage());
      throw new SudokuDaoException("Failed to write SudokuBoard to file", e);
    }
  }

  @Override
  public SudokuBoard read(String name) throws SudokuDaoException {
    logger.debug("reading sudoku board");
    Path file = directory.resolve(name + ".ser");
    try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
      return (SudokuBoard) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      logger.debug(e.getMessage());
      throw new SudokuDaoException("Failed to read SudokuBoard from file", e);
    }
  }

  @Override
  public List<String> names() {
    List<String> result = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.ser")) {
      logger.info("listing sudoku boards");
      for (Path file : stream) {
        result.add(file.getFileName().toString().replace(".ser", ""));
      }
    } catch (IOException e) {
      logger.error(e.getMessage());
      throw new RuntimeException("Failed to read SudokuBoard names", e);
    }
    return result;
  }

  @Override
  public void close() {
    // just for closable to work
  }
}

package stec.dao;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import stec.model.SudokuBoard;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
  private final Path directory;

  public FileSudokuBoardDao(String dirName) {
    this.directory = Paths.get(dirName);
    try {
      Files.createDirectories(this.directory);
    } catch (IOException e) {
      throw new RuntimeException("Failed to create directory: " + e);
    }
  }

  @Override
  public void write(String name, SudokuBoard board) throws DaoException {
    Path file = directory.resolve(name + ".ser");
    try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
      oos.writeObject(board);
    } catch (IOException e) {
      throw new DaoException("Failed to write SudokuBoard to file", e);
    }
  }

  @Override
  public SudokuBoard read(String name) throws DaoException {
    Path file = directory.resolve(name + ".ser");
    try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
      return (SudokuBoard) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new DaoException("Failed to read SudokuBoard from file", e);
    }
  }

  @Override
  public List<String> names() {
    List<String> result = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.ser")) {
      for (Path file : stream) {
        result.add(file.getFileName().toString().replace(".ser", ""));
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to read SudokuBoard names", e);
    }
    return result;
  }

  @Override
  public void close() {
    // just for closable to work
  }
}

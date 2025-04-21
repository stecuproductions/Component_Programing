package stec.dao;

import stec.model.SudokuBoard;

public class SudokuBoardDaoFactory {
  private final String directory;

  public SudokuBoardDaoFactory(String directory) {
    this.directory = directory;
  }

  public Dao<SudokuBoard> getFileDao() {
    return new FileSudokuBoardDao(directory);
  }
}

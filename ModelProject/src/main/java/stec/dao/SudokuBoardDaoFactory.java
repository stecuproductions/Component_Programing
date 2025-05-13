package stec.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stec.model.SudokuBoard;


public class SudokuBoardDaoFactory {

  private static final Logger log = LoggerFactory.getLogger(SudokuBoardDaoFactory.class);

  private final String directory;

  public SudokuBoardDaoFactory(String directory) {
    this.directory = directory;
  }

  public Dao<SudokuBoard> getFileDao() {
    log.info("getFileDao called");
    return new FileSudokuBoardDao(directory);
  }
}

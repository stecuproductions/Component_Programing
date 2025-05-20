package stec.dao;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stec.model.SudokuBoard;


public class SudokuBoardDaoFactory {

  private static final Logger log = LoggerFactory.getLogger(SudokuBoardDaoFactory.class);

  private  String directory;

  public SudokuBoardDaoFactory() {
    this.directory = null;
  }

  public  SudokuBoardDaoFactory(String directory) {
    this.directory = directory;
  }

  public Dao<SudokuBoard> getFileDao() {
    log.info("getFileDao called");
    return new FileSudokuBoardDao(directory);
  }

  public Dao<SudokuBoard> getDatabaseDao() {
    Dotenv dotenv = Dotenv.load();
    String url = dotenv.get("DB_URL");
    String user = dotenv.get("DB_USER");
    String password = dotenv.get("DB_PASSWORD");
    log.info("getDatabaseDao called");
    return new JdbcSudokuBoardDao(url, user, password);
  }
}

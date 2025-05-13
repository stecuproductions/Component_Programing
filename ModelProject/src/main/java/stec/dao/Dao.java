package stec.dao;

import java.util.List;

import stec.exceptions.SudokuDaoNamesException;
import stec.exceptions.SudokuDaoReadException;
import stec.exceptions.SudokuDaoWriteException;


public interface Dao<T> extends AutoCloseable {



  T read(String name) throws SudokuDaoReadException;


  void write(String name, T obj) throws SudokuDaoWriteException;



  List<String> names() throws SudokuDaoNamesException;
  
  @Override
  void close();
}

package stec.dao;

import stec.exceptions.SudokuDaoException;

import java.util.List;

public interface Dao<T> {

  T read(String name) throws SudokuDaoException;

  void write(String name, T obj) throws SudokuDaoException;

  List<String> names();
}

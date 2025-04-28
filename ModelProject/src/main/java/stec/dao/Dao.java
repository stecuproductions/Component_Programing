package stec.dao;

import java.util.List;

public interface Dao<T> {
  T read(String name) throws DaoException;

  void write(String name, T obj) throws DaoException;

  List<String> names();
}

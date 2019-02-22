package controller;

import java.util.List;
import model.IModel;

public interface BaseController<T extends IModel> {

  public List<T> getAll();

  public void insert(T model);

  public void insertList(List<T> list);

  public void update(T model);
}

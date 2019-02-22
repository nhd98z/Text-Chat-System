package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.UserModel;
import server.Server;

public class UserController implements BaseController<UserModel> {

  private static final String FILE_NAME = "users.txt";

  @Override
  public synchronized List<UserModel> getAll() {
    List<UserModel> list = new ArrayList<>();
    try {
      FileInputStream fileStream = new FileInputStream(new File(FILE_NAME));
      ObjectInputStream objectStream = new ObjectInputStream(fileStream);
      list = (List<UserModel>) objectStream.readObject();
      objectStream.close();
      fileStream.close();
      list.sort(new UserModel()::compareByName);
    } catch (Exception ex) {
      Server.serverUI.setStatus("Cannot find " + FILE_NAME);
    } finally {
      return list;
    }
  }

  @Override
  public void insert(UserModel model) {
    List<UserModel> list = getAll();
    list.add(model);
    insertList(list);
  }

  @Override
  public synchronized void insertList(List<UserModel> list) {
    try {
      FileOutputStream fileStream = new FileOutputStream(new File(FILE_NAME));
      ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
      objectStream.writeObject(list);
      objectStream.flush();
      objectStream.close();
      fileStream.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void update(UserModel model) {
    List<UserModel> list = getAll();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getUsername().equals(model.getUsername())) {
        list.set(i, model);
      }
    }
    insertList(list);
  }

  public void setAllUsersOffline() {
    List<UserModel> list = new UserController().getAll();
    list.stream().forEach(user -> {
      user.setIsOnline(false);
    });
    insertList(list);
  }

  public UserModel getUserByUsername(UserModel model) {
    List<UserModel> list = new UserController().getAll();
    for (UserModel user : list) {
      if (user.getUsername().equals(model.getUsername())) {
        return user;
      }
    }
    return null;
  }

  public void setOneUserStatus(UserModel model, boolean isOnline) {
    if (model == null) {
      return;
    }
    List<UserModel> list = new UserController().getAll();
    List<UserModel> newUsers = new ArrayList<>();
    list.stream().forEach(user -> {
      if (user.getUsername().equals(model.getUsername())) {
        user.setIsOnline(isOnline);
      }
      newUsers.add(user);
    });
    insertList(newUsers);
  }

  public boolean authenticate(UserModel model) {
    List<UserModel> list = new UserController().getAll();
    return list.stream().anyMatch(user -> user.getUsername().equals(model.getUsername()) && user.getPassword().equals(model.getPassword()));
  }

  /*------OFFLINE MESSAGE------*/
  public void increaseOm(String sender, String receiver) {
    List<UserModel> list = getAll();
    list.stream().filter(model -> (model.getUsername().equals(receiver))).forEachOrdered(model -> {
      model.increaseOm(sender);
      update(model);
    });
  }

  public void clearOm(String sender, String receiver) {
    List<UserModel> list = getAll();
    list.stream().filter(model -> (model.getUsername().equals(receiver))).forEachOrdered(model -> {
      model.clearOm(sender);
      update(model);
    });
  }

  public static void main(String[] args) {
    UserController controller = new UserController();
    UserModel usera = new UserModel("a", "a");
    UserModel userb = new UserModel("b", "b");
    UserModel userc = new UserModel("c", "c");
    controller.insert(usera);
    controller.insert(userb);
    controller.insert(userc);
    controller.increaseOm("a", "c");
    controller.increaseOm("a", "c");
    controller.increaseOm("b", "c");
    System.out.println(controller.getUserByUsername(userc).getTotalOm());
    System.out.println(controller.getUserByUsername(userc).getOm("a"));
    System.out.println(controller.getUserByUsername(userc).getOm("b"));
  }

}

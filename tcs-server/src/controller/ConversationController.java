package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.ConversationModel;
import server.Server;

public class ConversationController implements BaseController<ConversationModel> {

  private static final String FILE_NAME = "conversations.txt";

  @Override
  public synchronized List<ConversationModel> getAll() {
    List<ConversationModel> list = new ArrayList<>();
    try {
      FileInputStream fileStream = new FileInputStream(new File(FILE_NAME));
      ObjectInputStream objectStream = new ObjectInputStream(fileStream);
      list = (List<ConversationModel>) objectStream.readObject();
      objectStream.close();
      fileStream.close();
      list.sort(new ConversationModel()::compareBySender);
    } catch (Exception ex) {
      Server.serverUI.setStatus("Cannot find " + FILE_NAME);
    } finally {
      return list;
    }
  }

  @Override
  public void insert(ConversationModel model) {
    if (isExistConversation(model)) {
      update(model);
    } else {
      // swapped sender-receiver model
      model = new ConversationModel(model.getSender(), model.getReceiver(), model.getConversation());
      System.out.println(model.getSender() + " - " + model.getReceiver() + "\n" + model.getConversation() + "\n---");
      List<ConversationModel> list = getAll();
      list.add(model);
      insertList(list);
    }
  }

  @Override
  public synchronized void insertList(List<ConversationModel> list) {
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

  /**
   * Update Conversation mới dựa trên cặp sender-receiver
   *
   * @param model
   */
  @Override
  public void update(ConversationModel model) {
    // swapped sender-receiver model
    model = new ConversationModel(model.getSender(), model.getReceiver(), model.getConversation());
    List<ConversationModel> list = getAll();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getSender().equals(model.getSender()) && list.get(i).getReceiver().equals(model.getReceiver())) {
        list.set(i, model);
      }
    }
    insertList(list);
  }

  public boolean isExistConversation(ConversationModel model) {
    // swapped sender-receiver model
    model = new ConversationModel(model.getSender(), model.getReceiver(), model.getConversation());
    List<ConversationModel> list = getAll();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getSender().equals(model.getSender()) && list.get(i).getReceiver().equals(model.getReceiver())) {
        return true;
      }
    }
    return false;
  }

  /*
   Get conversation của cặp sender-receiver
   */
  public ConversationModel getOne(ConversationModel model) {
    model = new ConversationModel(model.getSender(), model.getReceiver(), model.getConversation());
    for (ConversationModel element : getAll()) {
      if (element.getSender().equals(model.getSender()) && element.getReceiver().equals(model.getReceiver())) {
        return element;
      }
    }
    return null;
  }

  public static void main(String[] args) {
    ConversationController controller = new ConversationController();
    controller.insert(new ConversationModel("a", "b", "a: hello b"));
    controller.insert(new ConversationModel("b", "a", "a: hello b\nb: i'm b, hello a"));
    controller.insert(new ConversationModel("a", "c", "a: dmm c ạ"));
    controller.insert(new ConversationModel("c", "a", "a: dmm c ạ\nc: thế á? đcmm luôn"));
    System.out.println(controller.getOne(new ConversationModel("a", "b", "")));
    System.out.println(controller.getOne(new ConversationModel("a", "c", "")));
    System.out.println(controller.getOne(new ConversationModel("b", "a", "")));
    System.out.println(controller.getOne(new ConversationModel("c", "a", "")));
  }

}

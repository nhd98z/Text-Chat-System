package ui;

import action.*;
import client.Client;
import java.awt.Component;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;
import model.UserModel;

public abstract class BaseUI extends javax.swing.JFrame {

  protected Client client;
  public UserModel user;

  {
    // block initializer
    this.init(this.getClass().getName().split("UI")[0].toUpperCase());
    myInitComponents();
  }

  protected void init(String title) {
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setTitle(title);
  }

  public BaseUI() {
  }

  public BaseUI(Client client) {
    this.client = client;
  }

  protected void clearData(Component... components) {
    for (Component component : components) {
      if (component instanceof JTextComponent) {
        ((JTextComponent) component).setText("");
      }
    }
  }

  protected void showError(BaseUI UI, String message) {
    JOptionPane.showMessageDialog(UI, message, "ERROR", JOptionPane.ERROR_MESSAGE);
  }

  protected void showSuccess(BaseUI UI, String message) {
    JOptionPane.showMessageDialog(UI, message, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
  }

  protected void showUI(BaseUI UI, int x, int y) {
    UI.setLocation(x, y);
    UI.clearData(UI.getComponents());
    UI.setVisible(true);
  }

  protected void exitProgram(UserModel user) {
    ExitProgramAction request1 = new ExitProgramAction(user);
    RefreshListAction request2 = new RefreshListAction(null);
    try {
      this.client.writer.writeObject(request1);
      this.client.writer.flush();
      this.client.writer.writeObject(request2);
      this.client.writer.flush();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  protected abstract void myInitComponents();

  protected abstract void updateData();

  public abstract void handleResponse(BaseAction response);
}

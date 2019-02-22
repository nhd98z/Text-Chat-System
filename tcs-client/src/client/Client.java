package client;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import ui.*;

public class Client {

  public Socket client;
  public ObjectOutputStream writer;
  public ObjectInputStream reader;
  public ClientThread clientThread;

  public RegisterUI registerUI = null;
  public LoginUI loginUI = null;
  public ListUserUI listUserUI = null;
  public ReadOmUI readOmUI = null;
  public Map<String, ChatUI> chatMap;

  public Client() {
    try {
      // initialize client, writer, reader
      client = new Socket("localhost", 9898);
      writer = new ObjectOutputStream(client.getOutputStream());
      reader = new ObjectInputStream(client.getInputStream());

      // initialize thread and map   
      clientThread = new ClientThread(this);
      chatMap = new HashMap<>();
    } catch (IOException ex) {
      Client.exitClient(null); // Không connect được: báo lỗi -> đóng program của client luôn
    }
  }

  public void initLoginUI() {
    loginUI = new LoginUI(this);
    java.awt.EventQueue.invokeLater(() -> {
      loginUI.setVisible(true);
    });
  }

  public void initRegisterUI() {
    registerUI = new RegisterUI(this);
    java.awt.EventQueue.invokeLater(() -> {
      registerUI.setVisible(true);
    });
  }

  public void initListUserUI() {
    listUserUI = new ListUserUI(this);
    java.awt.EventQueue.invokeLater(() -> {
      listUserUI.setVisible(true);
    });
  }

  public void initReadOmUI() {
    readOmUI = new ReadOmUI(this);
    java.awt.EventQueue.invokeLater(() -> {
      readOmUI.setVisible(true);
    });
  }

  public void runClient() {
    // Cho thread chạy    
    clientThread.start();
    initLoginUI();
  }

  public final static void exitClient(BaseUI UI) {
    JOptionPane.showMessageDialog(UI, "Cannot connect to server", "ERROR", JOptionPane.ERROR_MESSAGE);
    System.exit(0);
  }

  public static void main(String[] args) {
    new Client().runClient();
  }

}

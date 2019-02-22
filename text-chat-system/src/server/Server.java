package server;

import action.BaseAction;
import action.RefreshListAction;
import controller.UserController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

  public static ServerSocket server;
  public static Map<String, ServerThread> clients;
  public static int noClient;
  public static ServerUI serverUI;

  public static void sendToAllClients(BaseAction action) throws IOException {
    for (Map.Entry<String, ServerThread> entry : clients.entrySet()) {
      if (!entry.getValue().nameOfServerThread.equals(Integer.toString(entry.getValue().noClient))) {
        if (action instanceof RefreshListAction) {
          serverUI.setStatus("[" + entry.getValue().nameOfServerThread + "]" + " do REFRESH LIST");
        }
        entry.getValue().writer.writeObject(action);
        entry.getValue().writer.flush();
      }
    }
  }

  public static void sendToOneClient(ServerThread socketOfServer, BaseAction action) throws IOException {
    socketOfServer.writer.writeObject(action);
    socketOfServer.writer.flush();
  }

  public static void main(String[] args) throws IOException {
    // Mở ServerUI
    java.awt.EventQueue.invokeLater(() -> {
      serverUI = new ServerUI();
      serverUI.setVisible(true);
      serverUI.setStatus("OPEN SERVER");
      serverUI.setStatus("SET ALL USERS OFFLINE");
    });

    // Mở socket    
    server = new ServerSocket(9898);
    clients = new HashMap<>();
    noClient = 0;

    // Restart tất cả user là offline
    new UserController().setAllUsersOffline();

    // run Server
    Socket client;
    while (true) {
      client = server.accept();
      noClient++;
      serverUI.setStatus("ACCEPT CLIENT " + noClient);
      ServerThread serverThread = new ServerThread(client, noClient);
      clients.put(Integer.toString(noClient), serverThread);
      serverThread.start();
    }
  }

}

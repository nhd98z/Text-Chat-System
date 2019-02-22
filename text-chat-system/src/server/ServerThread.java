package server;

import action.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

  public Socket socketOfServer;
  public int noClient;
  public ObjectOutputStream writer;
  public ObjectInputStream reader;
  public boolean run;
  public String nameOfServerThread;

  public ServerThread(Socket socketOfServer, int noClient) throws IOException {
    this.socketOfServer = socketOfServer;
    this.noClient = noClient;
    this.nameOfServerThread = Integer.toString(noClient);
    this.writer = new ObjectOutputStream(socketOfServer.getOutputStream());
    this.reader = new ObjectInputStream(socketOfServer.getInputStream());
    this.run = true;
  }

  // Server luôn nhận một request từ phía client gửi về (Object)
  // Sau khi nhận request, server xử lý request rồi gửi lại cho Client một response (Object)
  @Override
  public void run() {
    BaseAction request;
    BaseAction response = null;
    while (this.run) {
      try {
        request = (BaseAction) reader.readObject();
        response = ServerController.getResponse(request);

        if (!(response instanceof RefreshListAction)) {
          Server.serverUI.setStatus(response.user != null ? "[" + response.user.getUsername() + "]" + " do " + response.type.name() : response.type.name());
        }

        if (response instanceof LoginAction) {
          LoginAction castedResponse = (LoginAction) response;
          if (castedResponse.isAuthenticated == true && castedResponse.isUserOnline == false) {
            // login success => set nameOfServerThread                        
            this.nameOfServerThread = castedResponse.user.getUsername();
            Server.clients.remove(Integer.toString(noClient));
            Server.clients.put(nameOfServerThread, this);
            Server.serverUI.setStatus("SET NAME OF SERVER THREAD: " + Integer.toString(noClient) + " -> " + nameOfServerThread);
          }
        }

        if (response instanceof RefreshListAction
                || response instanceof GetConversationAction
                || response instanceof ChatAction) {
          Server.sendToAllClients(response);
        } else {
          Server.sendToOneClient(this, response);
        }
      } catch (IOException | ClassNotFoundException ex) {
        System.out.println("Reading");
      }
    }
  }

}

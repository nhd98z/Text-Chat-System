package client;

import action.*;
import java.util.Map.Entry;
import model.UserModel;
import ui.ChatUI;

public class ClientThread extends Thread {

  public Client client;
  public boolean run;

  public ClientThread(Client client) {
    this.run = true;
    this.client = client;
  }

  /*
   * Khi client gửi một request sang server
   * Request dưới dạng action đó sẽ đc server handle và tạo ra một response
   * Ngay sau đó server sẽ trả lại một response, và nhiệm vụ của hàm run này là đọc response đó
   */
  @Override
  public void run() {
    BaseAction response;
    while (this.run) {
      try {
        response = (BaseAction) client.reader.readObject();
        System.out.println(response.type);

        if (response instanceof ExitProgramAction) {
          // hủy tất cả UI, dừng ClientThread
          if (client.registerUI != null && client.registerUI.isDisplayable()) {
            client.registerUI.dispose();
          }
          if (client.loginUI != null && client.loginUI.isDisplayable()) {
            client.loginUI.dispose();
          }
          if (client.listUserUI != null && client.listUserUI.isDisplayable()) {
            client.listUserUI.dispose();
          }
          for (Entry<String, ChatUI> entry : client.chatMap.entrySet()) {
            if (entry.getValue().isVisible()
                    && entry.getValue().user.getUsername().equals(response.user.getUsername())) {
              entry.getValue().dispose();
            }
          }
          run = false;
        } // register
        else if (response instanceof RegisterAction) {
          client.registerUI.handleResponse(response);
        } // login
        else if (response instanceof LoginAction) {
          client.loginUI.handleResponse(response);
        } // refresh list of user
        else if (response instanceof RefreshListAction) {
          client.listUserUI.handleResponse(response);
        } // Server send GetConversationAction cho tất cả Client, handle hết
        else if (response instanceof GetConversationAction) {
          for (Entry<String, ChatUI> entry : client.chatMap.entrySet()) {
            ChatUI chatUI = entry.getValue();
            chatUI.handleResponse(response);
          }
        } // bật cửa sổ (ChatUI receiver chat to sender) của thằng client receiver lên khi nhận đc tin nhắn từ sender
        else if (response instanceof ChatAction) {
          // username = program này là của ai (của client nào)
          String username = client.listUserUI.user.getUsername();
          String sender = response.user.getUsername();
          String receiver = ((ChatAction) response).receiver;
          if (username.equals(receiver) && client.chatMap.get(sender) == null) {
            ChatUI receiverChatUI = new ChatUI(client);
            receiverChatUI.user = new UserModel(receiver, "");
            receiverChatUI.setReceiver(sender);
            receiverChatUI.setChatTo(receiver + " chat to " + sender);
            receiverChatUI.setLocation((int) (Math.random() * 1000), (int) (Math.random() * 1000));
            receiverChatUI.setVisible(true);
            client.chatMap.put(sender, receiverChatUI);
          }
        } // đóng cửa sổ chat với một người
        else if (response instanceof CloseChatWindowAction) {
          client.chatMap.remove(((CloseChatWindowAction) response).receiver);
        } // clear om
        else if (response instanceof ClearAndGetNewOmAction) {
          client.readOmUI.handleResponse(response);
        }
      } catch (Exception ex) {
        // Mất kết nối tới server
        ex.printStackTrace();
        Client.exitClient(
                client.registerUI != null && client.registerUI.isDisplayable() ? client.registerUI
                : client.loginUI != null && client.loginUI.isDisplayable() ? client.loginUI
                : client.listUserUI != null && client.listUserUI.isDisplayable() ? client.listUserUI
                : null);
      }
    }
  }

}

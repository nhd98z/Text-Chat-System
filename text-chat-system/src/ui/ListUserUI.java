package ui;

import action.BaseAction;
import action.GetConversationAction;
import action.RefreshListAction;
import client.Client;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.SwingUtilities;
import model.UserModel;

public class ListUserUI extends BaseUI {

  private DefaultListModel<String> model;

  public ListUserUI(Client client) {
    super(client);
    model = new DefaultListModel<>();
    list.setModel(model);
    list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    user = null;
  }

  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    lblUsername = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    list = new javax.swing.JList<>();
    btnChat = new javax.swing.JButton();
    btnCancel = new javax.swing.JButton();
    btnOfflineMessage = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jLabel1.setText("Login as");

    lblUsername.setText("?");

    jLabel2.setText("Registered users");

    jScrollPane1.setViewportView(list);

    btnChat.setText("Chat");
    btnChat.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnChatActionPerformed(evt);
      }
    });

    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCancelActionPerformed(evt);
      }
    });

    btnOfflineMessage.setText("Offline Message (-1)");
    btnOfflineMessage.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnOfflineMessageActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane1)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUsername))
              .addComponent(jLabel2))
            .addGap(0, 0, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(btnChat, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnOfflineMessage)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(lblUsername))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel2)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(btnChat)
          .addComponent(btnCancel)
          .addComponent(btnOfflineMessage))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
    this.dispose();
    super.exitProgram(user);
  }//GEN-LAST:event_btnCancelActionPerformed

  private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    this.dispose();
    super.exitProgram(user);
  }//GEN-LAST:event_formWindowClosing

  private void btnChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChatActionPerformed
    int index = list.getSelectedIndex();
    if (index == -1) {
      super.showError(this, "Choose one user to chat");
    } else {
      String sender = user.getUsername();
      String receiver = model.getElementAt(index).split("\\s")[0];
      // Bảo Client mở ChatUI sender chat to receiver 
      ChatUI chatUI = super.client.chatMap.get(receiver);
      // Nếu UI null hoặc UI có rồi nhưng đã dispose thì mới mở
      if (chatUI == null || (chatUI != null && chatUI.isDisplayable() == false)) {
        chatUI = new ChatUI(super.client);
        chatUI.user = user;
        chatUI.setReceiver(receiver);
        chatUI.setLocation((int)(Math.random() * 1000), (int)(Math.random() * 1000));
        chatUI.setChatTo(sender + " chat to " + receiver);
        chatUI.setVisible(true);
        super.client.chatMap.put(receiver, chatUI);
      }
      // Sau khi mở ChatUI thì get conversation của sender-receiver về
      GetConversationAction action = new GetConversationAction(user, receiver);
      try {
        super.client.writer.writeObject(action);
        super.client.writer.flush();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }//GEN-LAST:event_btnChatActionPerformed

  private void btnOfflineMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOfflineMessageActionPerformed
    client.initReadOmUI();
    client.readOmUI.setUser(super.user);
  }//GEN-LAST:event_btnOfflineMessageActionPerformed

  @Override
  protected void myInitComponents() {
    initComponents();
  }

  @Override
  protected void updateData() {
  }

  @Override
  public void handleResponse(BaseAction response) {
    if (response instanceof RefreshListAction) {
      RefreshListAction castedResponse = (RefreshListAction) response;
      if (castedResponse.user != null) {
        btnOfflineMessage.setText("Offline Messages (" + castedResponse.user.getTotalOm() + ")");
      }
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          model.clear();
          // Nếu response trả về một user khác null thì gán vào user
          // Và cập nhật om
          if (castedResponse.user != null) {
            if (user == null) {
              user = castedResponse.user;
              lblUsername.setText(user.getUsername());
            }
            user.setOmMap(castedResponse.user.getOmMap());
          }
          // Hiển thị list of user            
          for (UserModel u : ((RefreshListAction) response).listUsers) {
            if (!u.getUsername().equals(user.getUsername())) {
              model.addElement(u.isOnline() == true ? u.getUsername() + " (online)" : u.getUsername());
            }
          }
        }
      });
    }
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnCancel;
  private javax.swing.JButton btnChat;
  private javax.swing.JButton btnOfflineMessage;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JLabel lblUsername;
  private javax.swing.JList<String> list;
  // End of variables declaration//GEN-END:variables

}

package ui;

import action.BaseAction;
import action.ChatAction;
import action.CloseChatWindowAction;
import action.GetConversationAction;
import client.Client;
import java.awt.Event;
import javax.swing.text.DefaultCaret;

public class ChatUI extends BaseUI {

  private String receiver;
  private String message;

  public ChatUI(Client client) {
    super(client);
  }

  public void setChatTo(String string) {
    lblChatTo.setText(string);
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    lblChatTo = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    txtConversation = new javax.swing.JTextArea();
    btnClose = new javax.swing.JButton();
    txtMessage = new javax.swing.JTextField();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    lblChatTo.setText("? chat to ?");

    txtConversation.setColumns(20);
    txtConversation.setRows(5);
    jScrollPane1.setViewportView(txtConversation);

    btnClose.setText("Close");
    btnClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCloseActionPerformed(evt);
      }
    });

    txtMessage.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        txtMessageKeyReleased(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
              .addGroup(layout.createSequentialGroup()
                .addComponent(lblChatTo)
                .addGap(0, 0, Short.MAX_VALUE))))
          .addComponent(txtMessage, javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(btnClose)
            .addGap(0, 0, Short.MAX_VALUE)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(lblChatTo)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(btnClose)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  @Override
  protected void myInitComponents() {
    initComponents();
    txtConversation.setEditable(false);
    DefaultCaret caret = (DefaultCaret) txtConversation.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
  }

  @Override
  protected void updateData() {
  }

  @Override
  public void handleResponse(BaseAction response) {
    if (response instanceof GetConversationAction) {
      GetConversationAction castedResponse = (GetConversationAction) response;
      String responseSender = castedResponse.user.getUsername();
      String responseReceiver = castedResponse.receiver;
      if ((responseSender.equals(user.getUsername()) && responseReceiver.equals(receiver))
              || (responseSender.equals(receiver) && responseReceiver.equals(user.getUsername()))) {
        txtConversation.setText(castedResponse.conversation);
      }
    }
  }

  private void closeChatWindow() {
    this.dispose();
    CloseChatWindowAction action = new CloseChatWindowAction(user, receiver);
    try {
      client.writer.writeObject(action);
      client.writer.flush();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    closeChatWindow();

  }//GEN-LAST:event_formWindowClosing

  private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
    closeChatWindow();
  }//GEN-LAST:event_btnCloseActionPerformed

  private void txtMessageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMessageKeyReleased
    if (evt.getKeyCode() == Event.ENTER) {
      message = txtMessage.getText();
      if (!message.isEmpty()) {
        txtMessage.setText("");
        ChatAction chatAction = new ChatAction(user, receiver, message);
        GetConversationAction getConversationAction = new GetConversationAction(user, receiver);
        try {
          super.client.writer.writeObject(chatAction);
          super.client.writer.flush();
          super.client.writer.writeObject(getConversationAction);
          super.client.writer.flush();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }//GEN-LAST:event_txtMessageKeyReleased

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnClose;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JLabel lblChatTo;
  private javax.swing.JTextArea txtConversation;
  private javax.swing.JTextField txtMessage;
  // End of variables declaration//GEN-END:variables
}

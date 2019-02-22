package ui;

import action.BaseAction;
import action.ClearAndGetNewOmAction;
import action.GetConversationAction;
import action.RefreshListAction;
import client.Client;
import java.util.Map.Entry;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.SwingUtilities;
import model.UserModel;

public class ReadOmUI extends BaseUI {

  private DefaultListModel<String> model;

  public ReadOmUI(Client client) {
    super(client);
    model = new DefaultListModel<>();
    list.setModel(model);
    list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    user = null;
  }

  public void setUser(UserModel user) {
    super.user = user;
    txtUsername.setText(user.getUsername());
    for (Entry<String, Integer> entry : user.getOmMap().entrySet()) {
      model.addElement(entry.getKey() + " (" + Integer.toString(entry.getValue()) + ")");
    }
  }

  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    txtUsername = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    list = new javax.swing.JList<>();
    btnRead = new javax.swing.JButton();
    btnClose = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jLabel1.setText("Login as");

    txtUsername.setText("?");

    jScrollPane1.setViewportView(list);

    btnRead.setText("Read");
    btnRead.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnReadActionPerformed(evt);
      }
    });

    btnClose.setText("Close");
    btnClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCloseActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(txtUsername)
            .addGap(0, 0, Short.MAX_VALUE)))
        .addContainerGap())
      .addGroup(layout.createSequentialGroup()
        .addComponent(btnRead)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(btnClose)
        .addGap(0, 78, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(txtUsername))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(btnRead)
          .addComponent(btnClose))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    this.dispose();
  }//GEN-LAST:event_formWindowClosing

  private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
    this.dispose();
  }//GEN-LAST:event_btnCloseActionPerformed

  private void btnReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadActionPerformed
    int index = list.getSelectedIndex();
    if (index == -1) {
      super.showError(this, "Choose one user to read");
    } else {
      String userWhoSendOm = model.getElementAt(index).split("\\s")[0];
      ClearAndGetNewOmAction clearAction = new ClearAndGetNewOmAction(user, userWhoSendOm);
      RefreshListAction refreshAction = new RefreshListAction(user);
      try {
        super.client.writer.writeObject(clearAction);
        super.client.writer.flush();
        super.client.writer.writeObject(refreshAction);
        super.client.writer.flush();
      } catch (Exception ex) {
        ex.printStackTrace();
      }

      // Mở ChatUI, phần này copy từ ListUserUI sang
      String sender = user.getUsername();
      String receiver = model.getElementAt(index).split("\\s")[0];
      // Bảo Client mở ChatUI sender chat to receiver 
      ChatUI chatUI = super.client.chatMap.get(receiver);
      // Nếu UI null hoặc UI có rồi nhưng đã dispose thì mới mở
      if (chatUI == null || (chatUI != null && chatUI.isDisplayable() == false)) {
        chatUI = new ChatUI(super.client);
        chatUI.user = user;
        chatUI.setReceiver(receiver);
        chatUI.setLocation((int) (Math.random() * 1000), (int) (Math.random() * 1000));
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
  }//GEN-LAST:event_btnReadActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnClose;
  private javax.swing.JButton btnRead;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JList<String> list;
  private javax.swing.JLabel txtUsername;
  // End of variables declaration//GEN-END:variables

  @Override
  protected void myInitComponents() {
    initComponents();
  }

  @Override
  protected void updateData() {
  }

  @Override
  public void handleResponse(BaseAction response) {
    if (response instanceof ClearAndGetNewOmAction) {
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          model.clear();
          for (Entry<String, Integer> entry : response.user.getOmMap().entrySet()) {
            model.addElement(entry.getKey() + " (" + Integer.toString(entry.getValue()) + ")");
          }
        }
      });
    }
  }
}

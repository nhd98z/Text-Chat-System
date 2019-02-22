package ui;

import action.BaseAction;
import action.LoginAction;
import action.RefreshListAction;
import client.Client;
import java.io.IOException;
import model.UserModel;

public class LoginUI extends BaseUI {

  private String username, password;

  public LoginUI(Client client) {
    super(client);
    user = null;
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    txtUsername = new javax.swing.JTextField();
    txtPassword = new javax.swing.JPasswordField();
    btnLogin = new javax.swing.JButton();
    btnCancel = new javax.swing.JButton();
    btnRegister = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jLabel1.setText("Login");

    jLabel2.setText("Password");

    btnLogin.setText("Login");
    btnLogin.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnLoginActionPerformed(evt);
      }
    });

    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCancelActionPerformed(evt);
      }
    });

    btnRegister.setText("Register");
    btnRegister.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnRegisterActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
        .addGap(16, 16, 16)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addGroup(layout.createSequentialGroup()
            .addGap(24, 24, 24)
            .addComponent(jLabel1))
          .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(btnLogin)
          .addComponent(btnCancel)
          .addComponent(btnRegister))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    this.dispose();
    super.exitProgram(user);
  }//GEN-LAST:event_formWindowClosing

  private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
    this.dispose();
    super.exitProgram(user);
  }//GEN-LAST:event_btnCancelActionPerformed

  private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
    updateData();
    if (this.isAnyTextFieldBlanked()) {
      return;
    }
    LoginAction request = new LoginAction(user);
    try {
      super.client.writer.writeObject(request);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }//GEN-LAST:event_btnLoginActionPerformed

  private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
    this.dispose();
    super.client.initRegisterUI();
  }//GEN-LAST:event_btnRegisterActionPerformed

  @Override
  protected void myInitComponents() {
    initComponents();
  }

  @Override
  protected void updateData() {
    username = txtUsername.getText();
    password = new String(txtPassword.getPassword());
    user = new UserModel(username, password);
  }

  @Override
  public void handleResponse(BaseAction response) {
    LoginAction castedResponse = (LoginAction) response;
    if (castedResponse.isAuthenticated == false) {
      super.showError(this, "Username or password is wrong");
    } else if (castedResponse.isUserOnline == true) {
      super.showError(this, username + " is online");
    } else {
      // login success
      super.showSuccess(this, "Login successful");
      // Bật ListUserUI
      this.dispose();
      super.client.initListUserUI();
      // Gửi một request RefreshListActiefresh
      RefreshListAction request = new RefreshListAction(user);
      try {
        super.client.writer.writeObject(request);
        super.client.writer.flush();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private boolean isAnyTextFieldBlanked() {
    String message = " cannot empty.";
    if (username.isEmpty()) {
      message = "Username" + message;
    } else if (password.isEmpty()) {
      message = "Password" + message;
    } else {
      return false;
    }
    super.showError(this, message);
    return true;
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnCancel;
  private javax.swing.JButton btnLogin;
  private javax.swing.JButton btnRegister;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JPasswordField txtPassword;
  private javax.swing.JTextField txtUsername;
  // End of variables declaration//GEN-END:variables
}

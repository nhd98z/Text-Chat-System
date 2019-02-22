package ui;

import action.BaseAction;
import action.RegisterAction;
import client.Client;
import java.io.IOException;
import model.UserModel;

public class RegisterUI extends BaseUI {

  private String fullname, username, password, rePassword;

  public RegisterUI(Client client) {
    super(client);
    user = null;
  }

  @Override
  protected void myInitComponents() {
    this.initComponents();
  }

  @Override
  protected void updateData() {
    fullname = txtFullname.getText();
    username = txtUsername.getText();
    password = new String(txtPassword.getPassword());
    rePassword = new String(txtRePassword.getPassword());
    user = new UserModel(username, password);
  }

  @Override
  public void handleResponse(BaseAction response) {
    RegisterAction castedResponse = (RegisterAction) response;
    if (castedResponse.isExistUsername) {
      // error
      super.showError(this, "Duplicate username");
    } else {
      // register success
      super.showSuccess(this, "Register successful");
    }
  }

  private boolean isPasswordMatched() {
    if (password.equals(rePassword)) {
      return true;
    } else {
      super.showError(this, "Password is not match");
      txtPassword.setText("");
      txtRePassword.setText("");
      return false;
    }
  }

  private boolean isAnyTextFieldBlanked() {
    String message = " cannot empty.";
    if (fullname.isEmpty()) {
      message = "Fullname" + message;
    } else if (username.isEmpty()) {
      message = "Username" + message;
    } else if (password.isEmpty()) {
      message = "Password" + message;
    } else if (rePassword.isEmpty()) {
      message = "Re-Password" + message;
    } else {
      return false;
    }
    super.showError(this, message);
    return true;
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel5 = new javax.swing.JLabel();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    txtFullname = new javax.swing.JTextField();
    txtUsername = new javax.swing.JTextField();
    txtPassword = new javax.swing.JPasswordField();
    txtRePassword = new javax.swing.JPasswordField();
    btnRegister = new javax.swing.JButton();
    btnClose = new javax.swing.JButton();

    jLabel5.setText("jLabel5");

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent evt) {
        formWindowClosing(evt);
      }
    });

    jLabel1.setText("Full name");

    jLabel2.setText("Login");

    jLabel3.setText("Password");

    jLabel4.setText("Re-type password");

    btnRegister.setText("Register");
    btnRegister.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnRegisterActionPerformed(evt);
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
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jLabel4)
          .addComponent(jLabel3)
          .addComponent(jLabel2)
          .addComponent(jLabel1))
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(btnRegister)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnClose))
          .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
            .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtRePassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel4)
          .addComponent(txtRePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(btnRegister)
          .addComponent(btnClose))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
    this.updateData();
    // blank text | password match
    if (this.isAnyTextFieldBlanked() || !this.isPasswordMatched()) {
      return;
    }
    // text have white space
    if (username.matches(".*\\s+.*")) {
      super.showError(this, "Username cannot have white space");
      return;
    }
    // Full name chả để làm gì
    RegisterAction request = new RegisterAction(user);
    try {
      super.client.writer.writeObject(request);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }//GEN-LAST:event_btnRegisterActionPerformed

  private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
    this.dispose();
    super.client.initLoginUI();
  }//GEN-LAST:event_btnCloseActionPerformed

  private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    this.dispose();
    super.client.initLoginUI();
  }//GEN-LAST:event_formWindowClosing

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnClose;
  private javax.swing.JButton btnRegister;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JTextField txtFullname;
  private javax.swing.JPasswordField txtPassword;
  private javax.swing.JPasswordField txtRePassword;
  private javax.swing.JTextField txtUsername;
  // End of variables declaration//GEN-END:variables
}

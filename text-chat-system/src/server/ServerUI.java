package server;

import javax.swing.text.DefaultCaret;

public class ServerUI extends javax.swing.JFrame {

  public ServerUI() {
    initComponents();
    this.setResizable(false);
    DefaultCaret caret = (DefaultCaret) txtStatus.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
  }

  public void setStatus(String status) {
    txtStatus.setText(txtStatus.getText() + "\n" + status);
  }

  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane2 = new javax.swing.JScrollPane();
    txtStatus = new javax.swing.JTextArea();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    txtStatus.setColumns(20);
    txtStatus.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
    txtStatus.setRows(5);
    jScrollPane2.setViewportView(txtStatus);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                            .addContainerGap())
    );
    layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                            .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JTextArea txtStatus;
  // End of variables declaration//GEN-END:variables
}

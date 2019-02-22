package model;

import java.io.Serializable;

public class ConversationModel implements IModel, Serializable {

  private String sender;
  private String receiver;
  private String conversation;

  public ConversationModel() {
  }

  public ConversationModel(String sender, String receiver, String conversation) {
    if (sender.compareTo(receiver) > 0) {
      String temp = sender;
      sender = receiver;
      receiver = temp;
    }
    this.sender = sender;
    this.receiver = receiver;
    this.conversation = conversation;
  }

  public String getSender() {
    return sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public String getConversation() {
    return conversation;
  }

  public void setConversation(String conversation) {
    this.conversation = conversation;
  }

  public int compareBySender(ConversationModel a, ConversationModel b) {
    return a.sender.compareTo(b.sender);
  }

  @Override
  public String toString() {
    return "ConversationModel{" + "sender=" + sender + ", receiver=" + receiver + ", conversation=" + conversation + '}';
  }
  
  

}

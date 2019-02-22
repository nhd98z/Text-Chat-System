package action;

import model.UserModel;
import utility.Constant;

public class ChatAction extends BaseAction {

  public String receiver;
  public String message;

  public ChatAction(UserModel user, String receiver, String message) {
    super(Constant.Action.CHAT, user);
    this.receiver = receiver;
    this.message = message;
  }

}

package action;

import model.UserModel;
import utility.Constant;

public class CloseChatWindowAction extends BaseAction {

  public String receiver;

  public CloseChatWindowAction(UserModel user, String receiver) {
    super(Constant.Action.CLOSE_CHAT_WINDOW, user);
    this.receiver = receiver;
  }

}

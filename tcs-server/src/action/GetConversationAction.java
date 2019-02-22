package action;

import model.UserModel;
import utility.Constant;

public class GetConversationAction extends BaseAction {

  public String receiver;
  public String conversation;

  public GetConversationAction(UserModel user, String receiver) {
    super(Constant.Action.GET_CONVERSATION, user);
    this.receiver = receiver;
  }

}

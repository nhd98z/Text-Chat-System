package action;

import model.UserModel;
import utility.Constant;

public class ClearAndGetNewOmAction extends BaseAction {

  public String userWhoSendOm;

  public ClearAndGetNewOmAction(UserModel user, String userWhoSendOm) {
    super(Constant.Action.CLEAR_AND_GET_OM, user);
    this.userWhoSendOm = userWhoSendOm;
  }

}

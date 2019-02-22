package action;

import model.UserModel;
import utility.Constant;

public class RegisterAction extends BaseAction {

  public boolean isExistUsername;

  public RegisterAction(UserModel user) {
    super(Constant.Action.REGISTER, user);
  }

}

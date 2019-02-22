package action;

import model.UserModel;
import utility.Constant;

public class LoginAction extends BaseAction {

  public boolean isAuthenticated;
  public boolean isUserOnline;

  public LoginAction(UserModel user) {
    super(Constant.Action.LOGIN, user);
    this.isAuthenticated = false;
    this.isUserOnline = false;
  }

}

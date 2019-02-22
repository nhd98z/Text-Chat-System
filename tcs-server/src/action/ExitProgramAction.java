package action;

import model.UserModel;
import utility.Constant;

public class ExitProgramAction extends BaseAction {

  public ExitProgramAction(UserModel user) {
    super(Constant.Action.EXIT_PROGRAM, user);
  }

}

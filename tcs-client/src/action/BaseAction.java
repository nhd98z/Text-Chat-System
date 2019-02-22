package action;

import utility.Constant;
import java.io.Serializable;
import model.UserModel;

public abstract class BaseAction implements Serializable {

  public Constant.Action type;
  public UserModel user;

  public BaseAction(Constant.Action type, UserModel user) {
    this.type = type;
    this.user = user;
  }

}

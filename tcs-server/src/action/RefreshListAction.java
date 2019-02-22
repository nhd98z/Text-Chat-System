package action;

import java.util.List;
import model.UserModel;
import utility.Constant;

public class RefreshListAction extends BaseAction {

  public List<UserModel> listUsers;

  public RefreshListAction(UserModel user) {
    super(Constant.Action.REFRESH_LIST_OF_USER, user);
  }  

}

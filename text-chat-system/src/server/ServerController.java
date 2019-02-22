package server;

import model.*;
import action.*;
import controller.*;

public class ServerController {

  // Xử lý request biến nó thành response và return nó
  public static ConversationController conversationController = new ConversationController();
  public static UserController userController = new UserController();

  public static BaseAction getResponse(BaseAction request) {
    if (request instanceof ChatAction) {
      return getResponse((ChatAction) request);
    } else if (request instanceof ClearAndGetNewOmAction) {
      return getResponse((ClearAndGetNewOmAction) request);
    } else if (request instanceof CloseChatWindowAction) {
      return getResponse((CloseChatWindowAction) request);
    } else if (request instanceof ExitProgramAction) {
      return getResponse((ExitProgramAction) request);
    } else if (request instanceof GetConversationAction) {
      return getResponse((GetConversationAction) request);
    } else if (request instanceof LoginAction) {
      return getResponse((LoginAction) request);
    } else if (request instanceof RefreshListAction) {
      return getResponse((RefreshListAction) request);
    } else if (request instanceof RegisterAction) {
      return getResponse((RegisterAction) request);
    }
    return request;
  }

  public static BaseAction getResponse(ChatAction request) {
    // Nối message vào conversation của 2 thằng
    String sender = request.user.getUsername();
    String receiver = request.receiver;
    String message = request.message;
    ConversationModel model = conversationController.getOne(new ConversationModel(sender, receiver, ""));
    String conversation = model != null ? model.getConversation() + "\n" : "";
    conversationController.insert(new ConversationModel(sender, receiver, conversation + sender + ": " + message));
    // Tăng offline message nếu receiver offline
    if (userController.getUserByUsername(new UserModel(receiver, "")).isOnline() == false) {
      userController.increaseOm(sender, receiver);
    }
    return request;
  }

  public static BaseAction getResponse(ClearAndGetNewOmAction request) {
    String userWhoSendOm = request.userWhoSendOm;
    userController.clearOm(userWhoSendOm, request.user.getUsername());
    // get new offline message
    request.user = userController.getUserByUsername(request.user);
    return request;
  }

  public static BaseAction getResponse(CloseChatWindowAction request) {
    return request;
  }

  public static BaseAction getResponse(ExitProgramAction request) {
    // Set user này về offline
    // Refresh lish of user
    userController.setOneUserStatus(request.user, false);
    // Xóa user khỏi map của Server
    Server.clients.remove(request.user.getUsername());
    return request;
  }

  public static BaseAction getResponse(GetConversationAction request) {
    String sender = request.user.getUsername();
    String receiver = request.receiver;
    ConversationModel model = conversationController.getOne(new ConversationModel(sender, receiver, ""));
    request.conversation = model == null ? "" : model.getConversation();
    return request;
  }

  public static BaseAction getResponse(LoginAction request) {
    // Kiểm tra authenticate và xem user có online không
    // Qua đc 2 bước là login thành công
    // Login thành công thì: set user online và refresh list of user
    if (userController.authenticate(request.user)) {
      request.isAuthenticated = true;
    }
    UserModel account = userController.getUserByUsername(request.user);
    if (account != null && account.isOnline() == true) {
      request.isUserOnline = true;
    }
    // login success -> set user online
    if (request.isAuthenticated == true && request.isUserOnline == false) {
      userController.setOneUserStatus(request.user, true);
    }
    return request;
  }

  public static BaseAction getResponse(RefreshListAction request) {
    request.listUsers = userController.getAll();
    // refresh user (offline message)
    if (request.user != null) {
      request.user = userController.getUserByUsername(request.user);
    }
    return request;
  }

  public static BaseAction getResponse(RegisterAction request) {
    // Kiểm tra trùng username, không trùng thì insert
    // Trả về biến setIsExistUsername để UI xử lý
    if (userController.getUserByUsername(request.user) != null) {
      request.isExistUsername = true;
    } else {
      request.isExistUsername = false;
      userController.insert(request.user);
    }
    return request;
  }

}

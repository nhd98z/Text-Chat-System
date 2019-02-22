package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class UserModel implements IModel, Serializable {

  private String username;
  private String password;
  private boolean isOnline;
  private Map<String, Integer> omMap;

  public UserModel() {
  }

  public UserModel(String username, String password) {
    this.username = username;
    this.password = password;
    this.isOnline = false;
    omMap = new HashMap<>();
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public boolean isOnline() {
    return isOnline;
  }

  public void setIsOnline(boolean isOnline) {
    this.isOnline = isOnline;
  }

  public int compareByName(UserModel a, UserModel b) {
    return a.getUsername().compareTo(b.getUsername());
  }

  public void setOmMap(Map<String, Integer> omMap) {
    this.omMap = omMap;
  }

  public Map<String, Integer> getOmMap() {
    return omMap;
  }

  public int getTotalOm() {
    int total = 0;
    for (Entry<String, Integer> entry : omMap.entrySet()) {
      total += entry.getValue();
    }
    return total;
  }

  public int getOm(String otherUser) {
    return omMap.get(otherUser) == null ? 0 : omMap.get(otherUser);
  }

  public void increaseOm(String otherUser) {
    int number = getOm(otherUser);
    omMap.put(otherUser, number + 1);
  }

  public void clearOm(String otherUser) {
    omMap.remove(otherUser);
  }

  @Override
  public String toString() {
    return "UserModel{" + "username=" + username + ", password=" + password + ", isOnline=" + isOnline + ", omMap=" + omMap + '}';
  }

}

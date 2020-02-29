package org.apache.hadoop.hive.ql.gy.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserAuth implements Serializable {

  @SerializedName("id")
  private int id;

  @SerializedName("user_name")
  private String userName;

  @SerializedName("super_user")
  private boolean superUser;

  @SerializedName("user_json_context")
  private String userJsonContext;

  @SerializedName("group_json_context")
  private String groupJsonContext;

  private Auth userAuth;

  private Auth groupAuth;

  private Auth allAuth;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public boolean isSuperUser() {
    return superUser;
  }

  public void setSuperUser(boolean superUser) {
    this.superUser = superUser;
  }

  public String getUserJsonContext() {
    return userJsonContext;
  }

  public void setUserJsonContext(String userJsonContext) {
    this.userJsonContext = userJsonContext;
  }

  public String getGroupJsonContext() {
    return groupJsonContext;
  }

  public void setGroupJsonContext(String groupJsonContext) {
    this.groupJsonContext = groupJsonContext;
  }

  public Auth getUserAuth() {
    return userAuth;
  }

  public void setUserAuth(Auth userAuth) {
    this.userAuth = userAuth;
  }

  public Auth getGroupAuth() {
    return groupAuth;
  }

  public void setGroupAuth(Auth groupAuth) {
    this.groupAuth = groupAuth;
  }

  public Auth getAllAuth() {
    return allAuth;
  }

  public void setAllAuth(Auth allAuth) {
    this.allAuth = allAuth;
  }

  @Override
  public String toString() {
    return "UserAuth{"
        + "id="
        + id
        + ", userName='"
        + userName
        + '\''
        + ", superUser="
        + superUser
        + ", userJsonContext='"
        + userJsonContext
        + '\''
        + ", groupJsonContext='"
        + groupJsonContext
        + '\''
        + ", userAuth="
        + userAuth
        + ", groupAuth="
        + groupAuth
        + '}';
  }
}

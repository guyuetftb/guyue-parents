package org.apache.hadoop.hive.ql.gy.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Auth implements Serializable {
  @SerializedName("user_id")
  private int userId;

  @SerializedName("db")
  private String db;

  @SerializedName("table")
  private String table;

  @SerializedName("excolumn")
  private String excolumn;

  @SerializedName("incolumn")
  private String incolumn;

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getDb() {
    return db;
  }

  public void setDb(String db) {
    this.db = db;
  }

  public String getExcolumn() {
    return excolumn;
  }

  public void setExcolumn(String excolumn) {
    this.excolumn = excolumn;
  }

  public String getIncolumn() {
    return incolumn;
  }

  public void setIncolumn(String incolumn) {
    this.incolumn = incolumn;
  }

  public String getTable() {
    return table;
  }

  public void setTable(String tables) {
    this.table = tables;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Auth auth = (Auth) o;
    return userId == auth.userId
        && Objects.equals(db, auth.db)
        && Objects.equals(table, auth.table)
        && Objects.equals(excolumn, auth.excolumn)
        && Objects.equals(incolumn, auth.incolumn);
  }

  @Override
  public int hashCode() {

    return Objects.hash(userId, db, table, excolumn, incolumn);
  }

  @Override
  public String toString() {
    return "Auth{"
        + "userId="
        + userId
        + ", db='"
        + db
        + '\''
        + ", table='"
        + table
        + '\''
        + ", excolumn='"
        + excolumn
        + '\''
        + ", incolumn='"
        + incolumn
        + '\''
        + '}';
  }
}

package com.quinyx.test.model;

public class CatResponse {

  private final int id;
  private final String nickName;
  private final Long age;

  public CatResponse(int id, String nickName, Long age) {
    this.id = id;
    this.nickName = nickName;
    this.age = age;
  }

  public int getId() {
    return id;
  }

  public String getNickName() {
    return nickName;
  }

  public Long getAge() {
    return age;
  }

}

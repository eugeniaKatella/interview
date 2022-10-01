package com.quinyx.test.model;

public class DogSummary {

  private final int id;
  private final String nickName;
  private final Double price;

  public DogSummary(int id, String nickName, Double price) {
    this.id = id;
    this.nickName = nickName;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public String getNickName() {
    return nickName;
  }

  public Double getPrice() {
    return price;
  }
}

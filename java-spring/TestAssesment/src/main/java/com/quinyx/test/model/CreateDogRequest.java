package com.quinyx.test.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateDogRequest {

  @NotEmpty
  private String nickName;

  @NotNull
  private Long age;

  @NotNull
  private Boolean canSwim;

  private Double price;

  @NotNull
  private String lastTrainingDate;

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public Long getAge() {
    return age;
  }

  public void setAge(Long age) {
    this.age = age;
  }

  public Boolean getCanSwim() {
    return canSwim;
  }

  public void setCanSwim(Boolean canSwim) {
    this.canSwim = canSwim;
  }

  public String getLastTrainingDate() {
    return lastTrainingDate;
  }

  public void setLastTrainingDate(String lastTrainingDate) {
    this.lastTrainingDate = lastTrainingDate;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}

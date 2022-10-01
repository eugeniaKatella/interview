package com.quinyx.test.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "dogs")
public class Dog {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "nick")
  private String nickName;

  @Enumerated(EnumType.ORDINAL)
  private Sound sound;

  private long age;

  private double price;

  private Boolean canSwim;

  private String lastTrainingDate;

  @CreatedDate
  private LocalDateTime addedAt;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public Sound getSound() {
    return sound;
  }

  public void setSound(Sound sound) {
    this.sound = sound;
  }

  public boolean isCanSwim() {
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

  public long getAge() {
    return age;
  }

  public void setAge(long age) {
    this.age = age;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public LocalDateTime getAddedAt() {
    return addedAt;
  }

  public void setAddedAt(LocalDateTime addedAt) {
    this.addedAt = addedAt;
  }
}

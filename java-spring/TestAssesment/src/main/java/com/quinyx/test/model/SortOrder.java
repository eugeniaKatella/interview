package com.quinyx.test.model;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum SortOrder {
  ASC("asc"),
  DESC("desc");

  private final String value;

  SortOrder(String value) {
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }

  public static SortOrder fromValue(String value) {
    return Arrays.stream(SortOrder.values()).filter(m -> m.value.equals(value)).findAny()
        .orElseThrow(() -> new RuntimeException("Wrong sort order"));
  }
}

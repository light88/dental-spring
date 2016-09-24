package com.dental.persistence.helperbean;

/**
 * Created by vrudyk on 7/2/2016.
 */
public enum ToothState {
  ALIVE("alive"),
  PULLED("pulled"),
  CURED("cured"),
  PENDING("pending"),
  CRITICAL("critical"),
  CORONA("corona");

  private String state;

  ToothState(String state) {
    this.state = state;
  }

  public static ToothState get(String value) {
    for (ToothState toothState : ToothState.values()) {
      if (toothState.name().equalsIgnoreCase(value)) {
        return toothState;
      }
    }
    return ToothState.ALIVE;
  }

  public String getState() {
    return state;
  }
}

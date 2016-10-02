package com.dental.exception;

import com.dental.bean.response.MessageBean;

/**
 * Created by vrudyk on 10/1/2016.
 */
public class EntityNotFountException extends RuntimeException {
  private MessageBean messageBean;

  public EntityNotFountException(String message) {
    super(message);
    this.messageBean = new MessageBean(message);
  }

  public MessageBean getMessageBean() {
    return messageBean;
  }
}

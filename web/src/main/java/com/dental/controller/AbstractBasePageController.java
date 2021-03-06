package com.dental.controller;

import com.dental.exception.NotFoundException;
import com.dental.exception.RequiredAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by light on 1/8/2015.
 */

@Controller
@ControllerAdvice
public abstract class AbstractBasePageController {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractBasePageController.class);

  @Autowired
  protected MessageSource messageSource;

  String renderView(String view) {
    return getViewFolder() + "/" + view;
  }

  protected abstract String getViewFolder();

  @ExceptionHandler(NotFoundException.class)
  public String notFound(NotFoundException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    String url = e.getUrl();
    LOG.debug("NotFoundHandler, url {}%s", url);
    if (!StringUtils.isEmpty(url)) {
      LOG.debug("NotFound URL, redirect" + url);
      response.sendRedirect(url);
    }
    return "404";
  }

  @ExceptionHandler(RequiredAuthenticationException.class)
  public void authenticationRequired(RequiredAuthenticationException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.sendRedirect("/login");
  }
}

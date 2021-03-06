package com.dental.controller;

import com.dental.exception.NotFoundException;
import com.dental.view.ViewFolderConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by light on 3/28/2015.
 */
@Controller
@RequestMapping("/admin")
public class AdminPageController extends AbstractBasePageController {

  @RequestMapping(value = "/login")
  public String login(HttpServletRequest request, HttpServletResponse response) throws NotFoundException {
    return "admin/login";
  }

  @RequestMapping(value = "/logout")
  public String logout() throws NotFoundException {
    return "auth/logout";
  }

  @RequestMapping(value = "/denied")
  public String denied() throws NotFoundException {
    return "auth/denied";
  }

  @RequestMapping(value = "/secure")
  public String secure() throws NotFoundException {
    return "auth/secure";
  }

  @Override
  protected String getViewFolder() {
    return ViewFolderConfig.FOLDER_ADMIN;
  }

}

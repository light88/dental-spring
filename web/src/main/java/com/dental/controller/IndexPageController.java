package com.dental.controller;

import com.dental.bean.SayText;
import com.dental.persistence.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by light on 1/1/2015.
 */

@Controller
public class IndexPageController extends AbstractBasePageController {

  private static final Logger LOG = LoggerFactory.getLogger(IndexPageController.class);

  @Autowired
  private SayText sayText;

  @RequestMapping(value = "/test/hello2")
  public String hello2(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                       User user) throws InterruptedException {
//    String m1 = messageSource.getMessage("u", null, Locale.getDefault());
//    String m2 = messageSource.getMessage("u", null, Locale.ENGLISH);

    while (true){
      Thread.sleep(2000);
      sayText.sayText();

    }

//    model.put("hello", 123);
//    model.put("user", user);
//    return "hello";
  }


  @ExceptionHandler(NullPointerException.class)
  public String handlerNPE(NullPointerException ex) {
    String message = ex.getMessage();
    LOG.info("HANDLER = " + message);
    return message;
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) throws Exception {
    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    final CustomDateEditor dateEditor = new CustomDateEditor(df, true) {
      @Override
      public void setAsText(String text) throws IllegalArgumentException {
        if ("today".equals(text)) {
          setValue(new Date());
        } else {
          super.setAsText(text);
        }
      }
    };
    binder.registerCustomEditor(Date.class, dateEditor);
  }

  @Override
  protected String getViewFolder() {
    return null;
  }
}

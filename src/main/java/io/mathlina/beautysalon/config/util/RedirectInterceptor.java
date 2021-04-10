package io.mathlina.beautysalon.config.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RedirectInterceptor implements HandlerInterceptor {

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

    if (modelAndView != null) {
      String args = request.getQueryString() != null ? request.getQueryString() + "?" : "";
      String url = request.getRequestURI() + args;
      response.setHeader("Turbolinks-Location", url);
    }
  }
}

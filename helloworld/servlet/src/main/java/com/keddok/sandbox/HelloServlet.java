package com.keddok.sandbox;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {
// private long callNumber = 0;

 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  response.getWriter().print("Hello World");
/*  try {
   Thread.sleep(100);
  }
  catch(java.lang.InterruptedException ex)
  {
   System.out.println(ex.getMessage());
  }*/
//  System.out.println(Long.toString(++callNumber));
 }

 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  response.getWriter().print("<xml>Posted successful</xml>");
 }
}
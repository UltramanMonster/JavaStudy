package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/5/27.
 */
public class HelloServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        out.println("<head><title>A Servlet</title></head>");
        out.println("<body>");

        String requestUrl = request.getRequestURI();
        System.out.println("requestUrl::"+requestUrl);

        out.println("<form action='" + requestUrl + "' method='post'>");
        out.println("<input type='text' name='name' />");
        out.println("<input type='submit' value='提交'>");
        out.println("</form>");
        out.println("");

        String name = request.getParameter("name");
        if (name != null && name.trim().length() > 0){
            out.println("您好！<b>" + name + "欢迎您。</b>");
        }

        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();

    }
}

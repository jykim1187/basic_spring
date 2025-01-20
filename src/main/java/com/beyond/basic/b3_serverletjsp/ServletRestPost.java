package com.beyond.basic.b3_serverletjsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/servlet/rest/post")
public class ServletRestPost extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String email =req.getParameter("email");

        System.out.println(name);
        System.out.println(email);

        resp.setContentType("text/plain");//리턴 타입을 문자열 형식으로 정한 것.
        resp.setCharacterEncoding("UTF-8");

        PrintWriter printWriter = resp.getWriter();
        printWriter.print("ok"); //위에서 text/plain으로 정했기에 "ok"문자열을 담을 수 있는것
        printWriter.flush();
    }
}

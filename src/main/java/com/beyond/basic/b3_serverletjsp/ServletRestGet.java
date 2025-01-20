package com.beyond.basic.b3_serverletjsp;

import com.beyond.basic.b1_hello.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//서블릿은 사용자의 request를 쉽게 처리하고, 사용자에게 response를 쉽게 조립해주는 기술(컨트롤러가 이 서블릿 기술을 근간으로 발달한 것이다)
//서블릿에서는 url매핑을 메서드 단위가 아닌, 클래스 단위로 지정한다
//그러면 실행어플리케이션에 서블릿을 스캔하는 어노테이션을 따로 지정해야한다
@WebServlet("/servlet/rest/get")
public class ServletRestGet extends HttpServlet { //서블릿 기술을 사용하려면 위의 어노테이션과 HttpServlet을 상속해야한다
    @Override
//    HttpServletRequest에는 사용자의 요청정보가 담겨있음(따라서, 컨트롤러 단에서도 매개변수로 이 클래스를 사용하면 데이터의 형식이 무엇이든 req로 받을 수 있다)
//    HttpServletResponse에는 사용자에게 줘야할 응답정보를 담아야함
//    doGet메서드는 GetMapping같은 기능
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       Hello hello = new Hello();
       hello.setName("hongildong");
       hello.setEmail("hongildong@naver.com");
//       resp 객체에 header,body를 직접 조립해야함.
//        header조립
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(hello);
//      body조립
//      body에는 많은 양의 데이터가 담길 수 있으므로, 기본적으로 buffer를 사용.
        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush(); //버퍼를 통해 데이터가 조립되므로, 마지막에는 버퍼를 비워줘야함.
    }

}

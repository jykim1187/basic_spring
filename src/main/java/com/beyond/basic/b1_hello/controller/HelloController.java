package com.beyond.basic.b1_hello.controller;

import com.beyond.basic.b1_hello.domain.Hello;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//Component 어노테이션(Controller어노테이션에 내장되어있음)을 통해 별도의 객체를 생성할 필요가 없는, 싱글톤 객체를 생성한다
//Controller 어노테이션을 통해 쉽게 사용자의  http요청을 처리하고, http응답을 줄 수 있음
@Controller
//클래스 차원에 url매핑시에는 RequsetMapping 사용
@RequestMapping("/hello")
public class HelloController {
//case 1. 서버가 사용자에게 단순 String 데이터 return(get요청) -@ResponseBody
//case 2. 서버가 사용자에게 화면을 return(get요청) - ResposeBody가 없을 때

    @GetMapping("")//(/data/hello-->url패턴임)라고 원격지의 사용자가 요청을하면 helloworld가 http 문서 형태로 리턴되어 사용자가 확인할 수 있다
//    @ResponseBody
//    @ResposeBody가 없고, return 타입이 String인 경우 서버는 templates폴더 밑에 helloworld.html화면을 찾아 리턴한다
    public String helloWorld(){
        return "helloworld";
    }

//case 3. 서버가 사용자에게 json형식의 데이터를 return(get요청)
   @GetMapping("/json")
//   메서드 차원에서도 RequestMapping사용 가능하나 많이 사용하지 않는다.
//   @RequestMapping(value = "/json",method = RequestMethod.GET)
   @ResponseBody
    public Hello helloJson() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Hello h1 = new Hello("hongildong", "hongildong@naver.com");
//        String value = objectMapper.writeValueAsString(h1);
//        직접 json으로 직렬화(객체->json형식의 문자열) 할 필요 없이, return 타입을 클래스로 지정시에 자동으로 직렬화.
       Hello h1 = new Hello("hongildong", "hongildong@naver.com");
       return h1;
    }
    
    
    
//case 4. 사용자가 json데이터를 요청하되, parameter형식으로 특정객체 요청(get요청)
// parameter형식 : /hello/param1?name=hongildong
    @GetMapping("/param1")
    @ResponseBody
    public Hello Param1(@RequestParam(value="name")String inputName){ //RequestParam:파라미터를 꺼낼 수 있는 어노테이션
        Hello h1 = new Hello(inputName, "test@naver.com");
        return h1;
    }


// parameter 2개 이상 형식 : /hello/param2?name=hongildong&email=hong@naver.com
    @GetMapping("/param2")
    @ResponseBody
    public Hello Param2(@RequestParam(value="name")String inputName,@RequestParam(value="email")String inputEmail){ //RequestParam:파라미터를 꺼낼 수 있는 어노테이션
        Hello h1 = new Hello(inputName, inputEmail);
        return h1;
    }

//case 5.parameter가 많아질 경우, 데이터 바인딩을 통해 input값 처리(get요청)
// parameter 2개 이상 형식 : /hello/param2?name=hongildong&email=hong@naver.com
    @GetMapping("/param3")
    @ResponseBody
//  각 파라미터의 값이 Hello클래스의 각 변수에 mapping
//    public Hello Param3(Hello hello){ //스프링에서 Hello클래스에 있는 각 필드에 맞게 알아서 mapping하여 객체를 만듬
    public Hello Param3(@ModelAttribute Hello hello){ // 바인딩했다는 걸 명시하기 위해 modelattribute어노테이션을 붙이는 경우가 많다
        return hello;
    }

//case 6.화면을 retrun 해주되, 특정변수값을 동적으로 세팅(데이터를 화면에 꽃아서 동시에 보내는 일종의 mvc)
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value="name")String inputName, Model model){ // 그러면 여기 매개변수로 받은 inputName을 다시 화면에 전달해줘야하는데 그때필요한 것이 모델 객체
//        model객체는 특정 데이터를 화면에 전달해주는 역할
//        modelName이라는 키값에 value를 세팅하면 modelName 값 형태로 화면에 전달. 이때 html화면은 타임리프 언어를 써야함...
       model.addAttribute("modelName",inputName);
        return "helloworld2";
    }




}

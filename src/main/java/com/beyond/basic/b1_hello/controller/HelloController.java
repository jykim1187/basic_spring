package com.beyond.basic.b1_hello.controller;

import com.beyond.basic.b1_hello.domain.Hello;
import com.beyond.basic.b1_hello.domain.StudentReqDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    //case 7.화면을 return 해주되, 객체를 동적으로 세팅
    @GetMapping("/model-param2")
    public String modelParam2(@ModelAttribute Hello hello, Model model){
//        model객체는 특정 데이터를 화면에 전달해주는 역할
//        modelName이라는 키값에 value를 세팅하면 modelName 값 형태로 화면에 전달. 이때 html화면은 타임리프 언어를 써야함...
        model.addAttribute("modelHello",hello);
        return "helloworld3";
    }

//  case 8.pathvariable 방식을 통해 사용자로부터 값을 받아 화면 return
//  형식 : /hello/model-path/hogildong
//  예시 : /author/detail/1
//  pathvariable방식은 url을 통해 자원구조를 명확하게 표현할 떄 사용.(좀 더 restful한 방식)
    @GetMapping("/model-path/{inputName}")
 public String modelPath(@PathVariable String inputName, Model model){
        model.addAttribute("modelName",inputName);
        return "helloworld2";
    }
// 01월 10일!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    사용자에게 name,email을 입력할 수 있는 화면을 주는 메서드 정의
    @GetMapping("/form-view")
    public String formView(){
        return"form-view";
    }

//    case1.form  데이터 형식의 post요청 처리(텍스트만 있는 application/x-www~)
//    형식 : form태그를 쓰면 ?name=xxx&email=yyy 의 파라미터형식의 데이터가 http문서의 body에 들어온다. 그래서 RequestParam형식으로 받을 수 있어 데이터바인딩으로도 받을수 있음
    @PostMapping("/form-view")
    @ResponseBody
    public String formPost1(@ModelAttribute Hello hello){
        System.out.println(hello);
        return "ok!";
    }

//   case2. form 데이터 형식의 post요청 처리(file+text형식)
//    url 패턴:form-file-view
    @GetMapping("/form-file-view")
    public String formFileView(){
        return "form-file-view";
    }

    @PostMapping("/form-file-view")
    @ResponseBody
//    자바에서 파일을 처리하는 클래스 : MultipartFile
//    아예 처음부터 hello객체 필드에 MultipartFile 속성에 넣으면 바로 Hello객체로만 받아도 사진도 받을 수 있다.
    public String formPost2(Hello hello, @RequestParam(value = "photo")MultipartFile photo){
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }

//    case3. js를 활용한 form데이터 전송(text만)
    @GetMapping("/axious-form-view")
    public String axiousFormView(){
        return"axious-form-view";
    }
//  js를 통한 form형식도 마찬가지로  ?name=xxx&email=yyy로 들어온다
    @PostMapping("/axious-form-view")
    @ResponseBody
    public String formPost3(@ModelAttribute Hello hello){
        System.out.println(hello);
        return "ok"; //이때 form형식과는 다르게 html화면에 ok가 리턴되지않는다 그 이유는 리턴값이 js의 result변수에 할당되었기 때문
    }

    //    case4. js를 활용한 form데이터 전송(text+file)
        @GetMapping("/axious-form-fileview")
    public  String axiousFormFileview(){
            return "axious-form-fileview";
        }

        @PostMapping("/axious-form-fileview")
        @ResponseBody
    public String formPost4(Hello hello, @RequestParam(value = "photo")MultipartFile photo){
            System.out.println(hello);
            System.out.println(photo.getOriginalFilename());
            return "ok";
        }

//        case 5. js 활용한 form데이터 전송(text+멀티 file)

    @GetMapping("/axious-form-multifileview")
    public  String axiousFormMultiFileview(){
        return "axious-form-multifileview";
    }

    @PostMapping("/axious-form-multifileview")
    @ResponseBody
    public String formPost5(Hello hello, @RequestParam(value = "photos") List<MultipartFile> photos){
        System.out.println(hello);
        for(int i=0; i<photos.size(); i++){
            System.out.println(photos.get(i).getOriginalFilename());
        }
        return "ok";
    }

//    case6.js를 활용한 json데이터 전송
//    형식 : {name:"hogildong", email:"hong@naver.com"}
    @GetMapping("/axious-json-view")
    public String axiousJsonView(){
        return "axious-json-view";
    }

    @PostMapping("/axious-json-view")
    @ResponseBody
    public String axiousJsonViewPost(@RequestBody Hello hello){ // RequestBody를 빼면 파라미터 형식 json형식은 RequestBody를 붙여야한다
        System.out.println(hello);
        return "ok";
    }

//    case7. 중첩된 JSON데이터 처리
//    예시데이터(student객체) : {name:"hongildong", email:"hong@naver.com", scores:[{math:60},{music:70},{english:60}]}

    @GetMapping("/axious-nested-json-view")
    public String axiousNestedJsonView(){
        return "axious-nested-json-view";
    }

    @PostMapping("/axious-nested-json-view")
    @ResponseBody
    public String axiousNestedJsonViewPost(@RequestBody StudentReqDto studentReqDto){
        System.out.println(studentReqDto.getName());
        System.out.println(studentReqDto.getEmail());
        for(StudentReqDto.Score score : studentReqDto.getScores()){
            System.out.println(score.getSubject());
            System.out.println(score.getPoint());

        }

        return "ok";
    }

//    case 8. json과 file처리
//    file처리는 기본적으로 form형식을 통해 처리한다.
//    그래서, json과 file을 동시에 처리하려면, form형식안에 json과 file을 넣어 처리한다.
    @GetMapping("/axious-json-file-view")
    public String axiousJsonFileView(){
        return "axious-json-file-view";
    }

//    데이터형식 : ?hello={name:"hongildong", email:"hing@naver.com"}&photo=이미지
    @PostMapping("/axious-json-file-view")
    @ResponseBody
    public String axiousJsonFileViewPost(
//            @RequestParam(value = "hello") String helloString,
//            @RequestParam(value = "photo") MultipartFile photo
//            RequestPart는 json과 file을 함께 처리할 때 많이 사용
            @RequestPart("hello") Hello hello,
            @RequestPart("photo") MultipartFile photo
    ) throws JsonProcessingException {
//    RequestParam으로 받을 때
    //        ObjectMapper objectMapper = new ObjectMapper();
//        Hello h1 = objectMapper.readValue(helloString, Hello.class);
//        System.out.println(h1);
//        System.out.println(photo.getOriginalFilename());

//        RequestPart로 받을 떄
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }
}

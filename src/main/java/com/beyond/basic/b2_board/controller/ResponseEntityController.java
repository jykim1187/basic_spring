package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.dtos.CommonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("response/entity")
public class ResponseEntityController {
//    case1. @ResponseStatus 어노테이션 사용
    @GetMapping("/annotation1")
    @ResponseStatus(HttpStatus.OK)
    public String annotation1(){
        return "ok";
    }
// 그런데 case1은 trycatch에서 에러가 발생해도 무조건 해당 스태이터스를 보내게 되서 잘 사용하지 않음->유연성이 떨어짐
    @PostMapping("/annotation2")
    @ResponseStatus(HttpStatus.CREATED)
    public String annotation2(){
        return "ok";
    }

//    case2. 메서드 체이닝 방식 : ResponseEntity의 클래스 사용
    @GetMapping("chainning1")
    public ResponseEntity<Member> chainning1(){
//        header부에 200ok, body에 member형태의 json
        Member member = new Member("hong","ho@naver.com","12341234");
//        return ResponseEntity.ok(member);
          return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

//    case3.ResponseEntity 객체를 직접 생성하여 custom하는 방식
    @GetMapping("/custom1")
//    <>안에 Member,?도 가능. Object는 모든 객체의 조상이니까 당연히 가능
    public ResponseEntity<Object> custom1(){
        Member member = new Member("hong","ho@naver.com","12341234");
        return new ResponseEntity<>(member,HttpStatus.CREATED);
    }

//    실무에서 가장 많이 쓰이는 패턴이다.
    @GetMapping("/custom2")
    public ResponseEntity<?> custom2(){
        Member member = new Member("hong","ho@naver.com","12341234");
//        header : 상태코드 +상태메세지, body: 상태코드,상태메시지,객체
        return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(),"member is found",member),HttpStatus.OK);
    }



}

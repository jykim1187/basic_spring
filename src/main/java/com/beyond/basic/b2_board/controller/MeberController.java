package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.dtos.MemberCreateDto;
import com.beyond.basic.b2_board.dtos.MemberListRes;
import com.beyond.basic.b2_board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MeberController {
//    의존성주입(Dependency Injection) 방법1. Autowired 어노테이션 사용 : 필드 주입

        @Autowired //싱글톤 객체를 여기 컨트롤러로 가지고 오겠다.
        private MemberService memberService;



//    홈화면
    @GetMapping("")
    public String memberHome(){
        return "member/home";
    }

//    회원 목록 조회
    @GetMapping("/list")
    public String memberList(Model model){
//        MemberListRes는 멤버 조회용 객체
//        멤버를 조회해서 모델 매개체를 통해 mvc패턴으로 프론트에 쏴줘야하는데 컨트롤러에서 직접 멤버를 조회하는게 아니라 서비스 계층에서!
//        그런데 문제는 멤버서비스 서비스 어노테이션이 붙으면서 싱글톤 객체를 가지고 있으므로 싱글톤 객체를 여기 컨트롤러 클래스에 가지고 와야함
        List<MemberListRes> memberListResList = memberService.findAll();
        model.addAttribute("memberList",memberListResList);
        return "member/member-list";
    }

//    회원 상세 조회(name,email,password 조회되게)-->id값 넘겨박아서 id값 출력
    @GetMapping("/detail/{id}")
    public String memberDetail(@PathVariable long id){
        System.out.println(id);
        return "member/member-detail";
    }

//    회원가입(name,email,password)=>MemberCreateDto
    @GetMapping("/create")
    public String memberCreate(){
        return "member/member-create";
    }

//    새로운 화면 리턴이 아닌 url 재호출을 통해 redirect
    @PostMapping("/create")
    public String memberCreatePost(@ModelAttribute MemberCreateDto mcd){
        memberService.save(mcd);
        return "redirect:/member/list"; //redirect는 html화면을 직접 리턴하는 것이 아니라 url링크가 Getmapping된 메서드를 리턴
    }
}

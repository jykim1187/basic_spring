package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.dtos.MemberCreateDto;
import com.beyond.basic.b2_board.dtos.MemberDetailDto;
import com.beyond.basic.b2_board.dtos.MemberListRes;
import com.beyond.basic.b2_board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

//    의존성주입(Dependency Injection) 방법1. Autowired 어노테이션 사용 : 필드 주입
//        방법1은 final로 설정불가
//        @Autowired //싱글톤 객체를 여기 컨트롤러로 가지고 오겠다.
//        private MemberService memberService;

//    의존성주입(DI) 방법2. 생성자 주입방식(가장 많이 사용하는 방식)
//생성자 주입 방식의 원리는 객체가 생성될 때 필요한 의존성을 생성자에 전달하여 주입하는 것
//    장점1)final을 통해 상수로 사용가능(안정성 향상, 재할당 못하니까) 2)다형성 구현 가능 3)순환참조를 컴파일 타임에 체크할 수 있다

//    private final MemberService memberService;
////    싱글톤 객체로 만들어지는 시점에 아래 생성자가 호출된다.
////    생성자가 하나밖에 없을 때에는 Autowired생략가능
//    @Autowired //매개변수에 대한 Autowired
//    public MemberController(MemberService memberService){
//        this.memberService =memberService ;
//    }

//    의존성 주입 방법 3.RequiredArgs 어노테이션 사용방법
//    RequiredArgs : 반드시 초기화 되어야하는 필드(final키워드,Nonnull어노테이션 등)를 대상으로 생성자를 자동으로 만들어주는 어노테이션.
    private final MemberService memberService;



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
    public String memberDetail(@PathVariable Long id, Model model) {
//        name,email,password뿌려준다고 가정
        try {
            MemberDetailDto dto = memberService.findById(id);
            model.addAttribute("member", dto);
            System.out.println(id);
            return "member/member-detail";
        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/member-error";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/member-error";
        }
    }

//    회원가입(name,email,password)=>MemberCreateDto
    @GetMapping("/create")
    public String memberCreate(){
        return "member/member-create";
    }


//    새로운 화면 리턴이 아닌 url 재호출을 통해 redirect
    @PostMapping("/create")
    public String memberCreatePost(@ModelAttribute MemberCreateDto mcd, Model model){
       try{
           memberService.save(mcd);
           return "redirect:/member/list"; //redirect는 html화면을 직접 리턴하는 것이 아니라 url링크가 Getmapping된 메서드를 리턴
       } catch(IllegalArgumentException e){
           model.addAttribute("errorMessage", e.getMessage());
           return "member/member-error";
       }

    }
}

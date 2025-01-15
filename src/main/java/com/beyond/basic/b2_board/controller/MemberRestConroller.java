package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.dtos.MemberCreateDto;
import com.beyond.basic.b2_board.dtos.MemberDetailDto;
import com.beyond.basic.b2_board.dtos.MemberListRes;
import com.beyond.basic.b2_board.dtos.MemberUpdateDto;
import com.beyond.basic.b2_board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

//레스트컨트롤러를 쓰면 모든 메서드에 리스폰스바디메서드가 붙여진다.
@RestController
@RequestMapping("/member/rest/")
public class MemberRestConroller {


//   의존성 주입방법 2번째
    private final MemberService memberService;
    public MemberRestConroller(MemberService memberService) {
        this.memberService = memberService;
    }


    //    회원 목록 조회
    @GetMapping("/list")
    public List<MemberListRes> memberList(){
        List<MemberListRes> memberListResList = memberService.findAll();
        return memberListResList;
    }


    @GetMapping("/detail/{id}")
    public MemberDetailDto memberDetail(@PathVariable Long id) {
            MemberDetailDto dto = memberService.findById(id);
            return dto;
    }

    //    회원가입(name,email,password)=>MemberCreateDto
    @PostMapping("/create")
    public String memberCreate(@RequestBody MemberCreateDto memberCreateDto){
        memberService.save(memberCreateDto);
        return "okay!";
    }

//   get:조회, post:등록, patch:부분수정, put:대체, delete:삭제
//    프론트에도 axious.patch가 따로있음
    @PatchMapping("/update/pw")
    public String memberUpdate(@RequestBody MemberUpdateDto memberUpdateDto){
        memberService.updatePw(memberUpdateDto);
        return "Okay";
    }


}

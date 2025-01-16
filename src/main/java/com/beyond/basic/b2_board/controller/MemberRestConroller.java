package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.dtos.*;
import com.beyond.basic.b2_board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
    public ResponseEntity<?> memberList(){
        List<MemberListRes> memberListResList = memberService.findAll();
        return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(),
                "memberlist is founded",memberListResList),HttpStatus.OK);
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<?> memberDetail(@PathVariable Long id) {
        try{
            MemberDetailDto dto = memberService.findById(id);
            return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(),"member is successfully found",dto),HttpStatus.OK);
        } catch(EntityNotFoundException e) {
            return new ResponseEntity<>(new CommonErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    //    회원가입(name,email,password)=>MemberCreateDto
    @PostMapping("/create")
    public ResponseEntity<?> memberCreate(@RequestBody MemberCreateDto memberCreateDto){
        try {
           Member member =  memberService.save2(memberCreateDto);
            return new ResponseEntity<>(new CommonDto(HttpStatus.CREATED.value(),"member is created",member.getId()),HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

//   get:조회, post:등록, patch:부분수정, put:대체, delete:삭제
//    프론트에도 axious.patch가 따로있음
    @PatchMapping("/update/pw")
    public ResponseEntity<?> memberUpdate(@RequestBody MemberUpdateDto memberUpdateDto){
      try{
          memberService.updatePw(memberUpdateDto);
          return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(), "member is updated","success"),HttpStatus.OK);
      } catch(EntityNotFoundException e){
          return new ResponseEntity<>(new CommonErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage()),HttpStatus.NOT_FOUND);
      }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id){
        try{
            memberService.delete(id);
            return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(), "member is deleted","member is deleted"),HttpStatus.OK);
        }catch(EntityNotFoundException e){
           return new ResponseEntity<>(new CommonErrorDto(HttpStatus.NOT_FOUND.value(), e.getMessage()),HttpStatus.NOT_FOUND);
        }

    }

    }

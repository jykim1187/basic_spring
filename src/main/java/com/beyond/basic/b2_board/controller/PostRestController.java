package com.beyond.basic.b2_board.controller;

import com.beyond.basic.b2_board.dtos.CommonDto;
import com.beyond.basic.b2_board.dtos.PostCreateDto;
import com.beyond.basic.b2_board.dtos.PostDetailDto;
import com.beyond.basic.b2_board.dtos.PostListRes;
import com.beyond.basic.b2_board.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/post")
@RestController
public class PostRestController {
//    의존성 주입2
    private final PostService postService;

    public PostRestController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/create")
//   Valid와 NotEmpty등 검증 어노테이션이 한 쌍(Dto에서 NotEmpty,여기서 valid)
    public ResponseEntity<?> save(@Valid @RequestBody PostCreateDto postCreateDto){
        postService.save(postCreateDto);
        return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(),"success","sucess"),HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        List<PostListRes> postList = postService.findAll();
        return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(),"sucess",postList),HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id){
         PostDetailDto postDetailDto=postService.findById(id);
         return new ResponseEntity<>(new CommonDto(HttpStatus.OK.value(),"sucess",postDetailDto),HttpStatus.OK);
    }

}

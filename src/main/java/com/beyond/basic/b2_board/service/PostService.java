package com.beyond.basic.b2_board.service;

import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.domain.Post;
import com.beyond.basic.b2_board.dtos.PostCreateDto;
import com.beyond.basic.b2_board.dtos.PostDetailDto;
import com.beyond.basic.b2_board.dtos.PostListRes;
import com.beyond.basic.b2_board.repository.MemberRepository;
import com.beyond.basic.b2_board.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //!!중요 @AutoWired는 생성자가 하나일 때 생략가능한거라 매개변수가 여러개여도 상관 x
    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    //    게시글 등록
    public void save(PostCreateDto postCreateDto){
        Member member = memberRepository.findById(postCreateDto.getMemberId()).orElseThrow(()->new EntityNotFoundException("없는 멤버id입니다"));
        Post post = postCreateDto.toEntityFromDto(member);
        postRepository.save(post);
    }

//    목록조회
    public List<PostListRes> findAll(){
       return postRepository.findAll().stream().map(p->p.toListDto()).collect(Collectors.toList());
    }
//    상세조회
    public PostDetailDto findById(Long id){
              Post post = postRepository.findById(id).orElseThrow(()->new EntityNotFoundException("없는 게시글입니다"));

//              PostDetailDto postDetailDto = new PostDetailDto(post.getId(),post.getTitle(),post.getContents(),
//                      memberRepository.findById(post.getMemberId()).get().getEmail());
//                return postDetailDto;
        return post.toDetailDto(post.getMember().getEmail());
    }

}

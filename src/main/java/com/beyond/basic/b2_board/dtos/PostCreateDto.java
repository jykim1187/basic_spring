package com.beyond.basic.b2_board.dtos;

import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostCreateDto {
    @NotEmpty
   private String title;
   private String contents;
   private Long memberId;



    public Post toEntityFromDto(Member member){
//        빌더패턴은 엔티티명.builder().이 사이에 원하는 변수 세팅한다.build();
//        return new Post(this.title,this.contents,this.memberId);
          return Post.builder().contents(this.contents).title(this.title).member(member).build(); //순서상관없이 갯수상관없이 유연하다
    }
}

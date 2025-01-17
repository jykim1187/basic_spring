package com.beyond.basic.b2_board.domain;

import com.beyond.basic.b2_board.dtos.PostCreateDto;
import com.beyond.basic.b2_board.dtos.PostDetailDto;
import com.beyond.basic.b2_board.dtos.PostListRes;
import com.beyond.basic.b2_board.repository.MemberRepository;
import com.beyond.basic.b2_board.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
//Builder어노테이션을 사용하여 빌더패턴으로 엔티티의 생성자를 구성.(이때 AllArgsConstructor가 필수적으로 선행해야한다)
//빌더패턴의 장점:매개변수의 순서와 개수를 유연하게 세팅할 수 있다.
@Builder
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String contents;
//    private Long memberId;
//    lazy(지연로딩)로 설정시 member객체를 사용하지 않는 한, member 테이블로 쿼리 발생하지 않음
    @ManyToOne(fetch = FetchType.LAZY) //포스트입장에서 멤버는 n대일이니까 //ManyToOne에서는 EAGER가 기본 설정
//    이에 반해 EAGER(즉시로딩)타입으로 설정시 사용하지 않아도 member테이블로 쿼리 발생
    @JoinColumn(name = "member_id") //프로그램에서는 객체지만 db에는 객체란 개념이 없다. 이때스프링이 자동으로 id를 추출해서db에저장.그때 칼럼이름을 알려주는 것
    private Member member;



    public PostListRes toListDto(){
//        return new PostListRes(this.id,this.title);
        return PostListRes.builder().id(this.id).title(this.title).build();
    }

    public PostDetailDto toDetailDto(String email){
        return PostDetailDto.builder().id(this.id).title(this.title).contents(this.contents).memberEmail(email).build();
    }


}

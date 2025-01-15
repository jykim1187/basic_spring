package com.beyond.basic.b2_board.repository;

import com.beyond.basic.b2_board.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//스프링데이터Jpa레포지토리임.
//SpringDataJpa가 되기 위해서는 JpaRepository를 상속해야하고, 상속시에 Entity명과 entity의 pk타입을 명시
//JpaRepository를 상속함으로써 JpaRepository의 주요기능(각종 CRUD기능이 사전에 구현되어있음) 상속
//
public interface MemberRepository extends JpaRepository<Member, Long> {
//    save,findAll,findById는 사전에 구현.(그래서 위에 <Member,Long>으로 Entity와 pk를 이거를 주면 자동으로 구현되게 전달해주는 거임. )
//    그 외에 다른 컬럼으로 조회할 때는 findBy+컬럼명 형식으로 선언만하면 실행시점에 구현되도록 미리 JpaRepository에 처리되어있음
    Optional<Member> findByEmail(String email);
}

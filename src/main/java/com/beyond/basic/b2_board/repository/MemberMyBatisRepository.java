//package com.beyond.basic.b2_board.repository;
//
//import com.beyond.basic.b2_board.domain.Member;
//import org.apache.ibatis.annotations.Mapper;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
////해당 레포지토리를 mybatis 레파지토리로 만들겠다는 어노테이션
//@Mapper
//public interface MemberMyBatisRepository {
////    mybatis 레파지토리에서는 필요한 메서드명(리턴타입,매개변수)만 정의하면 된다.,인터페이스니까 메서드 선언만해도 가능(즉{}없어도 됨)
////    쿼리는 다른 파일(Mapper File)에서 구현하는게 mybatis특징
//
//    List<Member> findAll();
//
//    Optional<Member> findById(Long id);
//
//    Optional<Member> findByEmail(String email);
//
//    void save(Member member);
//
//}

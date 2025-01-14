package com.beyond.basic.b2_board.repository;

import com.beyond.basic.b2_board.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository //레포지토리도 안에 컴포넌트 어노테이션이 내장->싱글톤 객체
public class MemberMemoryRepository {
    private List<Member> memberList = new ArrayList<>();
    public static Long id=1L;

    public List<Member> findAll(){
        return this.memberList;
    }

    public void save(Member member){
        this.memberList.add(member);
        id++;
    }

}

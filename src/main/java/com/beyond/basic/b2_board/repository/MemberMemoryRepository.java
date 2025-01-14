package com.beyond.basic.b2_board.repository;

import com.beyond.basic.b2_board.domain.Member;
import com.beyond.basic.b2_board.dtos.MemberCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Member> findById(Long id){
        Member member =null;

        for(Member m1 : memberList){
            if(m1.getId().equals(id)){
                member = m1;
            }
        }
        return Optional.ofNullable(member);
        }

    public Optional<Member> findByEmail(String email){
        Member member =null;

        for(Member m1 : memberList){
            if(m1.getEmail().equals(email)){
                member = m1;
            }
        }
        return Optional.ofNullable(member);
    }

    }



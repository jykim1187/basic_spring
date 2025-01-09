package com.beyond.basic.b1_hello.domain;

import lombok.*;

@Getter
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든매개변수 있는 생성자
//@Data : Getter,Setter,toString메서드 까지 포함하는 어노테이션
public class Hello {
    private String name;
    @Setter //email 변수에 관한 setter만 생성하고 싶을 떄 필드 위에다 세터어노테이션 설정
    private String email;




}

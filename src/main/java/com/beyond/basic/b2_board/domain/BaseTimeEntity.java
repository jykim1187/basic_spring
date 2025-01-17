package com.beyond.basic.b2_board.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
//기본적으로 Entity는 상속이 불가능하여 @MappedSuperclass를 붙여야 Entity와의 상속관계가 성립가능
@MappedSuperclass
@Getter //그런데 상속될때 이Member,Post엔티티에  Getter어노테이션이 적용이 안되므로 여기서 붙이고 간다
public class BaseTimeEntity {
    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;
}

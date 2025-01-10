package com.beyond.basic.b1_hello.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentReqDto {
    private String name;
    private String email;
    private List<StudentReqDto.Score> scores;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static public class Score{
        private String subject;
        private Integer point;
    }
}

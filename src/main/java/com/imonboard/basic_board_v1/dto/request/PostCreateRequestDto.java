package com.imonboard.basic_board_v1.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequestDto {
    private String title;
    private String content;
    private String author;
}

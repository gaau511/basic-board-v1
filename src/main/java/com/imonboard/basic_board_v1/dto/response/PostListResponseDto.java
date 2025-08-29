package com.imonboard.basic_board_v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostListResponseDto {
    private String title;
    private String author;
    private LocalDateTime createdAt;
}

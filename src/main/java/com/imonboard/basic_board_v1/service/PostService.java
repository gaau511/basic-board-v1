package com.imonboard.basic_board_v1.service;

import com.imonboard.basic_board_v1.dto.request.PostCreateRequestDto;
import com.imonboard.basic_board_v1.dto.response.PostDetailResponseDto;
import com.imonboard.basic_board_v1.dto.response.PostListResponseDto;
import com.imonboard.basic_board_v1.dto.request.PostUpdateRequestDto;

import java.util.List;

public interface PostService {
    Long createPost(PostCreateRequestDto createDto);

    void updatePost(Long postId, PostUpdateRequestDto updateDto);

    void deletePost(Long postId);

    List<PostListResponseDto> findAllPosts();

    PostDetailResponseDto findPostById(Long postId);
}

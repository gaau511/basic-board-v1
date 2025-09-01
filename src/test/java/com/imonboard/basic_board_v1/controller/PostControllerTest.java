package com.imonboard.basic_board_v1.controller;

import com.imonboard.basic_board_v1.dto.request.PostCreateRequestDto;
import com.imonboard.basic_board_v1.dto.request.PostUpdateRequestDto;
import com.imonboard.basic_board_v1.dto.response.PostDetailResponseDto;
import com.imonboard.basic_board_v1.dto.response.PostListResponseDto;
import com.imonboard.basic_board_v1.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @Test
    @DisplayName("게시글 생성")
    void createPost() {
        // given
        PostCreateRequestDto createDto = new PostCreateRequestDto("title", "content", "author");
        given(postService.createPost(any(PostCreateRequestDto.class))).willReturn(1L);

        // when
        ResponseEntity<Void> response = postController.createPost(createDto);

        // then
        assertThat(response.getStatusCode().isSameCodeAs(HttpStatus.CREATED));
        assertThat(response.getHeaders().getLocation().toString()).isEqualTo("/api/posts/1");
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void getAllPosts() {
        // given
        List<PostListResponseDto> posts = List.of(
                new PostListResponseDto("title1", "author1", LocalDateTime.now()),
                new PostListResponseDto("title2", "author2", LocalDateTime.now())
        );
        given(postService.findAllPosts()).willReturn(posts);

        // when
        ResponseEntity<List<PostListResponseDto>> response = postController.getAllPosts();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getTitle()).isEqualTo("title1");
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void getPostById() {
        // given
        Long postId = 1L;
        PostDetailResponseDto detailDto = new PostDetailResponseDto("title", "content", "author", LocalDateTime.now(), LocalDateTime.now());
        given(postService.findPostById(postId)).willReturn(detailDto);

        // when
        ResponseEntity<PostDetailResponseDto> response = postController.getPostById(postId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo("title");
    }

    @Test
    @DisplayName("게시글 수정")
    void updatePost() {
        // given
        Long postId = 1L;
        PostUpdateRequestDto updateDto = new PostUpdateRequestDto("new title", "new content");

        // when
        ResponseEntity<Void> response = postController.updatePost(postId, updateDto);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(postService).updatePost(eq(postId), any(PostUpdateRequestDto.class));
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() {
        // given
        Long postId = 1L;

        // when
        ResponseEntity<Void> response = postController.deletePost(postId);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(postService).deletePost(postId);
    }
}

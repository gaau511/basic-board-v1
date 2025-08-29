package com.imonboard.basic_board_v1.service;

import com.imonboard.basic_board_v1.dto.request.PostCreateRequestDto;
import com.imonboard.basic_board_v1.dto.response.PostDetailResponseDto;
import com.imonboard.basic_board_v1.dto.response.PostListResponseDto;
import com.imonboard.basic_board_v1.dto.request.PostUpdateRequestDto;
import com.imonboard.basic_board_v1.exception.ResourceNotFoundException;
import com.imonboard.basic_board_v1.model.Post;
import com.imonboard.basic_board_v1.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    @DisplayName("게시글 생성")
    void createPost() {
        // given
        PostCreateRequestDto createDto = new PostCreateRequestDto("title", "content", "author");
        Post post = new Post(1L, "title", "content", "author", LocalDateTime.now(), LocalDateTime.now());
        given(postRepository.save(any(Post.class))).willReturn(post);

        // when
        Long postId = postService.createPost(createDto);

        // then
        assertThat(postId).isEqualTo(1L);
        verify(postRepository).save(any(Post.class));
    }

    @Test
    @DisplayName("게시글 수정")
    void updatePost() {
        // given
        Long postId = 1L;
        PostUpdateRequestDto updateDto = new PostUpdateRequestDto("new title", "new content");
        Post post = new Post(postId, "title", "content", "author", LocalDateTime.now(), LocalDateTime.now());
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // when
        postService.updatePost(postId, updateDto);

        // then
        assertThat(post.getTitle()).isEqualTo("new title");
        assertThat(post.getContent()).isEqualTo("new content");
    }

    @Test
    @DisplayName("게시글 수정 - 게시글 없음")
    void updatePost_postNotFound() {
        // given
        Long postId = 1L;
        PostUpdateRequestDto updateDto = new PostUpdateRequestDto("new title", "new content");
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> postService.updatePost(postId, updateDto));
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() {
        // given
        Long postId = 1L;
        Post post = new Post(postId, "title", "content", "author", LocalDateTime.now(), LocalDateTime.now());
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // when
        postService.deletePost(postId);

        // then
        verify(postRepository).delete(post);
    }

    @Test
    @DisplayName("게시글 삭제 - 게시글 없음")
    void deletePost_postNotFound() {
        // given
        Long postId = 1L;
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> postService.deletePost(postId));
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void findAllPosts() {
        // given
        Post post1 = new Post(1L, "title1", "content1", "author1", LocalDateTime.now(), LocalDateTime.now());
        Post post2 = new Post(2L, "title2", "content2", "author2", LocalDateTime.now(), LocalDateTime.now());
        given(postRepository.findAll()).willReturn(List.of(post1, post2));

        // when
        List<PostListResponseDto> posts = postService.findAllPosts();

        // then
        assertThat(posts).hasSize(2);
        assertThat(posts.get(0).getTitle()).isEqualTo("title1");
        assertThat(posts.get(1).getTitle()).isEqualTo("title2");
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void findPostById() {
        // given
        Long postId = 1L;
        Post post = new Post(postId, "title", "content", "author", LocalDateTime.now(), LocalDateTime.now());
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // when
        PostDetailResponseDto postDto = postService.findPostById(postId);

        // then
        assertThat(postDto.getTitle()).isEqualTo("title");
        assertThat(postDto.getContent()).isEqualTo("content");
    }

    @Test
    @DisplayName("게시글 단건 조회 - 게시글 없음")
    void findPostById_postNotFound() {
        // given
        Long postId = 1L;
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> postService.findPostById(postId));
    }
}

package com.imonboard.basic_board_v1.service;

import com.imonboard.basic_board_v1.dto.request.PostCreateRequestDto;
import com.imonboard.basic_board_v1.dto.response.PostDetailResponseDto;
import com.imonboard.basic_board_v1.dto.response.PostListResponseDto;
import com.imonboard.basic_board_v1.dto.request.PostUpdateRequestDto;
import com.imonboard.basic_board_v1.exception.ResourceNotFoundException;
import com.imonboard.basic_board_v1.model.Post;
import com.imonboard.basic_board_v1.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public Long createPost(PostCreateRequestDto createDto) {
        Post post = new Post();
        post.setTitle(createDto.getTitle());
        post.setContent(createDto.getContent());
        post.setAuthor(createDto.getAuthor());
        postRepository.save(post);
        return post.getId();
    }

    @Override
    @Transactional
    public void updatePost(Long postId, PostUpdateRequestDto updateDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        if (updateDto.getTitle() != null) {
            post.setTitle(updateDto.getTitle());
        }
        if (updateDto.getContent() != null) {
            post.setContent(updateDto.getContent());
        }
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        postRepository.delete(post);
    }

    @Override
    public List<PostListResponseDto> findAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> new PostListResponseDto(post.getTitle(), post.getAuthor(), post.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public PostDetailResponseDto findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        return new PostDetailResponseDto(post.getTitle(), post.getContent(), post.getAuthor(), post.getCreatedAt(), post.getUpdatedAt());
    }
}

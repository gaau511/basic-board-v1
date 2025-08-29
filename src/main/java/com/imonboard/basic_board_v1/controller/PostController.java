package com.imonboard.basic_board_v1.controller;

import com.imonboard.basic_board_v1.dto.request.PostCreateRequestDto;
import com.imonboard.basic_board_v1.dto.response.PostDetailResponseDto;
import com.imonboard.basic_board_v1.dto.response.PostListResponseDto;
import com.imonboard.basic_board_v1.dto.request.PostUpdateRequestDto;
import com.imonboard.basic_board_v1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostCreateRequestDto createDto) {
        Long postId = postService.createPost(createDto);
        return ResponseEntity.created(URI.create("/api/posts/" + postId)).build();
    }

    @GetMapping
    public ResponseEntity<List<PostListResponseDto>> getAllPosts() {
        List<PostListResponseDto> posts = postService.findAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponseDto> getPostById(@PathVariable("id") Long postId) {
        PostDetailResponseDto post = postService.findPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable("id") Long postId, @RequestBody PostUpdateRequestDto updateDto) {
        postService.updatePost(postId, updateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}

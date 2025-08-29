package com.imonboard.basic_board_v1.repository;

import com.imonboard.basic_board_v1.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

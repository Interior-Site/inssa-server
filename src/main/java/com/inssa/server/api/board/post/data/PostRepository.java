package com.inssa.server.api.board.post.data;

import com.inssa.server.api.board.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
}

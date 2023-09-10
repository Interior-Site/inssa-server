package com.inssa.server.api.board.postlike.data;

import com.inssa.server.api.board.postlike.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserNoAndPostNo(Long userNo, Long postNo);
}

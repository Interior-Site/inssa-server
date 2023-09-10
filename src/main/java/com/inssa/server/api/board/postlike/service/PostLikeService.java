package com.inssa.server.api.board.postlike.service;

import com.inssa.server.api.board.postlike.data.PostLikeRepository;
import com.inssa.server.api.board.postlike.model.PostLike;
import com.inssa.server.common.code.ErrorCode;
import com.inssa.server.common.exception.InssaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("PostLikeService")
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

    public void createLike(Long postNo, Long userNo) {
        Optional<PostLike> findPostLike = postLikeRepository.findByUserNoAndPostNo(userNo, postNo);

        if(findPostLike.isPresent()) {
            throw new InssaException(ErrorCode.INTERNAL_SERVER_ERROR, "이미 공감한 글입니다.");
        }

        postLikeRepository.save(new PostLike(userNo, postNo));
    }

	public void deleteLike(Long postNo, Long userNo) {
		PostLike findPostLike = postLikeRepository.findByUserNoAndPostNo(userNo, postNo)
			.orElseThrow(() -> new InssaException(ErrorCode.INTERNAL_SERVER_ERROR, "공감하지 않은 글은 취소할 수 없습니다."));

		postLikeRepository.delete(findPostLike);
	}
}

package com.inssa.server.api.board.post.service;

import com.inssa.server.api.board.post.data.PostRepository;
import com.inssa.server.api.board.post.dto.PostListResponseDto;
import com.inssa.server.api.board.post.dto.PostRequestDto;
import com.inssa.server.api.board.post.dto.PostResponseDto;
import com.inssa.server.api.board.post.model.Post;
import com.inssa.server.api.board.post.model.PostType;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.share.board.BoardStatus;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("PostService")
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;

	@Transactional
	public Long createPost(PostRequestDto request) {
		Post post = postRepository.save(request.toEntity());
		return post.getNo();
	}

	public Page<PostListResponseDto> findPosts(PostType type, Pageable pageable) {
		return postRepository.findPosts(type, pageable);
	}

	public PostResponseDto findPost(Long postNo) {
		return postRepository.findPost(postNo);
	}

	public Long updatePost(PostRequestDto request) {
		Post post = findById(request.getPostNo());

		if(!Objects.equals(post.getUserNo(), request.getUserNo())) {
			throw new InssaException("작성자만 수정 가능합니다.");
		}

		if(post.getStatus() == BoardStatus.DELETED) {
			throw new InssaException("삭제된 글은 수정 불가능합니다.");
		}

		postRepository.save(post.updatePost(request.getTitle(), request.getContent()));

		return post.getNo();
	}

	public void deletePost(PostRequestDto request) {
		Post post = findById(request.getPostNo());
		postRepository.save(post.deletePost());
	}

	private Post findById(Long postNo) {
		return postRepository.findById(postNo)
			.orElseThrow(() -> new InssaException("해당하는 게시글이 없습니다."));
	}

	public String findPostSummaryById(Long id) {
		return postRepository.findPostSummaryById(id);
	}
}

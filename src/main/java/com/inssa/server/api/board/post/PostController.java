package com.inssa.server.api.board.post;

import com.inssa.server.api.board.post.dto.PostCreateRequestDto;
import com.inssa.server.api.board.post.dto.PostListResponseDto;
import com.inssa.server.api.board.post.dto.PostRequestDto;
import com.inssa.server.api.board.post.dto.PostResponseDto;
import com.inssa.server.api.board.post.dto.PostUpdateRequestDto;
import com.inssa.server.api.board.post.model.PostType;
import com.inssa.server.api.board.post.service.PostService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "post", description = "게시글 API")
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@Slf4j
public class PostController {
	private final PostService postService;

	@Operation(summary = "게시글 등록", tags = "post")
	@PreAuthorizeLogInUser
	@PostMapping("/post")
	public InssaApiResponse<Map<String, Object>> createPost(@RequestBody PostCreateRequestDto request, @AuthenticationPrincipal AuthUser user) {
		PostRequestDto serviceRequest = PostRequestDto.createBuilder()
			.postType(request.getPostType())
			.title(request.getTitle())
			.content(request.getContent())
			.userNo(user.getUserNo())
			.build();

		Long postNo = postService.createPost(serviceRequest);
		return InssaApiResponse.ok(ResponseCode.CREATED, Map.of("postNo", postNo));
	}

	@Operation(summary = "게시글 목록 조회", tags = "post")
	@GetMapping("/posts")
	public InssaApiResponse<Page<PostListResponseDto>> findPosts(
			@SortDefault(sort = "created_date", direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam PostType type) {

		return InssaApiResponse.ok(postService.findPosts(type, pageable));
	}

	@Operation(summary = "게시글 단건 조회", tags = "post")
	@GetMapping("/post")
	public InssaApiResponse<PostResponseDto> findPost(@RequestParam Long postNo) {
		return InssaApiResponse.ok(postService.findPost(postNo));
	}

	@Operation(summary = "게시글 수정", tags = "post")
	@PreAuthorizeLogInUser
	@PutMapping("/post")
	public InssaApiResponse<Map<String, Object>> updatePost(@RequestBody PostUpdateRequestDto request, @AuthenticationPrincipal AuthUser user) {
		PostRequestDto serviceRequest = PostRequestDto.updateBuilder()
			.postNo(request.getPostNo())
			.title(request.getTitle())
			.content(request.getContent())
			.userNo(user.getUserNo())
			.build();

		Long postNo = postService.updatePost(serviceRequest);
		return InssaApiResponse.ok(ResponseCode.UPDATED, Map.of("postNo", postNo));
	}

	@Operation(summary = "게시글 삭제", tags = "post")
	@PreAuthorizeLogInUser
	@PutMapping("/post/{postNo}")
	public InssaApiResponse<Map<String, Object>> deletePost(@PathVariable Long postNo, @AuthenticationPrincipal AuthUser user) {
		PostRequestDto serviceRequest = PostRequestDto.deleteBuilder()
			.postNo(postNo)
			.userNo(user.getUserNo())
			.build();

		postService.deletePost(serviceRequest);
		return InssaApiResponse.ok(ResponseCode.DELETED, Map.of("postNo", postNo));
	}
}

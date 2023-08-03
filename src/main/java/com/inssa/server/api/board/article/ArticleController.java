package com.inssa.server.api.board.article;

import com.inssa.server.api.board.article.dto.ArticleCreateRequestDto;
import com.inssa.server.api.board.article.dto.ArticleListResponseDto;
import com.inssa.server.api.board.article.dto.ArticleRequestDto;
import com.inssa.server.api.board.article.dto.ArticleUpdateRequestDto;
import com.inssa.server.api.board.article.model.ArticleType;
import com.inssa.server.api.board.article.service.ArticleService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.exception.InssaException;
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
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "article", description = "게시글 API")
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
	private final ArticleService articleService;

	@Operation(summary = "게시글 등록", tags = "article")
	@PreAuthorizeLogInUser
	@PostMapping("/article")
	public InssaApiResponse<Map<String, Object>> createArticle(@RequestBody ArticleCreateRequestDto request, @AuthenticationPrincipal AuthUser user) {
		ArticleRequestDto serviceRequest = ArticleRequestDto.createBuilder()
			.articleType(request.getArticleType())
			.title(request.getTitle())
			.content(request.getContent())
			.userNo(Long.parseLong(user.getUsername()))
			.build();

		Long articleNo = articleService.createArticle(serviceRequest);
		return InssaApiResponse.ok(ResponseCode.CREATED, Map.of("articleNo", articleNo));
	}

	@Operation(summary = "게시글 목록 조회", tags = "article")
	@GetMapping("/articles")
	public InssaApiResponse<Page<ArticleListResponseDto>> findArticles(
			@SortDefault(sort = "created_date", direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam ArticleType type) {

		return InssaApiResponse.ok(articleService.findArticles(type, pageable));
	}

	@Operation(summary = "게시글 수정", tags = "article")
	@PreAuthorizeLogInUser
	@PutMapping("/article")
	public InssaApiResponse<Map<String, Object>> updateArticle(@RequestBody ArticleUpdateRequestDto request, @AuthenticationPrincipal AuthUser user) {
		ArticleRequestDto serviceRequest = ArticleRequestDto.updateBuilder()
			.articleNo(request.getArticleNo())
			.title(request.getTitle())
			.content(request.getContent())
			.userNo(Long.parseLong(user.getUsername()))
			.build();

		Long articleNo = articleService.updateArticle(serviceRequest);
		return InssaApiResponse.ok(ResponseCode.UPDATED, Map.of("articleNo", articleNo));
	}

	@Operation(summary = "게시글 삭제", tags = "article")
	@PreAuthorizeLogInUser
	@PutMapping("/article/{articleNo}")
	public InssaApiResponse<Map<String, Object>> deleteArticle(@PathVariable Long articleNo, @AuthenticationPrincipal AuthUser user) {
		ArticleRequestDto serviceRequest = ArticleRequestDto.deleteBuilder()
			.articleNo(articleNo)
			.userNo(Long.parseLong(user.getUsername()))
			.build();

		articleService.deleteArticle(serviceRequest);
		return InssaApiResponse.ok(ResponseCode.DELETED, Map.of("articleNo", articleNo));
	}
}

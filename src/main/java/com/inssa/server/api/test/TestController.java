package com.inssa.server.api.test;

import com.inssa.server.api.test.dto.TestDto;
import com.inssa.server.api.test.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "test", description = "Test API")
@Slf4j
public class TestController {

	@Resource(name = "TestService")
	private TestService testService;

	@Tag(name = "test") // API 그룹의 이름 지정
	@Operation(summary = "test hello", description = "hello api example") // API 별 설명 추가
	@ApiResponses({ // 반환 타입 설정
		@ApiResponse(responseCode = "200", description = "OK !!"),
		@ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
		@ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
		@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
	})
	@GetMapping("/hello")
	public ResponseEntity<String> hello (
		@Parameter(description = "이름", required = true, example = "Park")
		@RequestParam
		String name) {
		return ResponseEntity.ok("hello " + name);
	}

	@Tag(name = "test")
	@Operation(summary = "test hello", description = "test select example")
	@GetMapping("/test")
	public List<TestDto> test () {
		return testService.findTest();
	}
}

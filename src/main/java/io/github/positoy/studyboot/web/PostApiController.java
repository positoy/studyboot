package io.github.positoy.studyboot.web;

import io.github.positoy.studyboot.web.dto.PostResponseDto;
import io.github.positoy.studyboot.web.dto.PostSaveRequestDto;
import io.github.positoy.studyboot.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/v1//posts")
    Long save(@RequestBody PostSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    PostResponseDto findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @PutMapping("/api/v1/posts/{id}")
    Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        return postService.update(id, requestDto);
    }
}

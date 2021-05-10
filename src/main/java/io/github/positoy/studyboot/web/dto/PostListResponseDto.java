package io.github.positoy.studyboot.web.dto;

import io.github.positoy.studyboot.web.domain.posts.Post;

import java.time.LocalDateTime;

public class PostListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostListResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}
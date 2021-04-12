package io.github.positoy.studyboot.web.dto;

import io.github.positoy.studyboot.web.domain.posts.Post;
import lombok.Getter;

@Getter
public class PostReadResponseDto {
    Long id;
    String title;
    String content;
    String author;

    public PostReadResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}

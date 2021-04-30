package io.github.positoy.studyboot.web;

import io.github.positoy.studyboot.web.domain.posts.Post;
import io.github.positoy.studyboot.web.domain.posts.PostRepository;
import io.github.positoy.studyboot.web.dto.PostResponseDto;
import io.github.positoy.studyboot.web.dto.PostSaveRequestDto;
import io.github.positoy.studyboot.web.dto.PostUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    void Post_저장한다() {
        String title = "제목";
        String content = "내용";
        String author = "글쓴이";

        PostSaveRequestDto dto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, dto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isPositive();

        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getAuthor()).isEqualTo(author);
    }

    @Test
    void Post_읽어온다() {
        Post savedPost = postRepository.save(Post.builder()
                .title("제목")
                .content("내용")
                .author("글쓴이")
                .build());

        String url = "http://localhost:" + port + "/api/v1/posts/1";
        ResponseEntity<PostResponseDto> responseEntity = restTemplate.getForEntity(url, PostResponseDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(responseEntity.getBody().getContent()).isEqualTo(savedPost.getContent());
        assertThat(responseEntity.getBody().getAuthor()).isEqualTo(savedPost.getAuthor());
    }

    @Test
    void Post_수정된다() {
        Post savedPost = postRepository.save(Post.builder()
                .title("제목")
                .content("내용")
                .author("글쓴이")
                .build());

        String newTitle = "변경된 제목";
        String newContent = "변경된 내용";
        PostUpdateRequestDto dto = PostUpdateRequestDto.builder()
                .title(newTitle)
                .content(newContent)
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts/" + savedPost.getId();
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, dto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(savedPost.getId());

        Post post = postRepository.findById(savedPost.getId()).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + savedPost.getId()));
        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContent()).isEqualTo(newContent);
    }
}
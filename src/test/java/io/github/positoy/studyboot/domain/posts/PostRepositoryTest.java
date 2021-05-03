package io.github.positoy.studyboot.domain.posts;

import io.github.positoy.studyboot.web.domain.posts.Post;
import io.github.positoy.studyboot.web.domain.posts.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @AfterEach
    public void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    void 게시글저장_불러오기() {
        String title = "제목";
        String content = "내용";
        String author = "글쓴이";
        postRepository.save(Post.builder().title(title).content(content).author(author).build());

        List<Post> postList = postRepository.findAll();

        Post post = postList.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getAuthor()).isEqualTo(author);
    }

    @Test
    void BaseTimeEntity_등록() {
        LocalDateTime now = LocalDateTime.of(2021, 5, 3, 0, 0, 0);
        postRepository.save(Post.builder().title("제목").content("내용").author("글쓴이").build());

        List<Post> postList = postRepository.findAll();

        Post post = postList.get(0);
        System.out.println("createdDate : " + post.getCreatedDate() + " modifiedDate : " + post.getModifiedDate());
        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }
}

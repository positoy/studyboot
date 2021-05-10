package io.github.positoy.studyboot.web.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM POST ORDER BY id desc", nativeQuery = true)
    List<Post> findAllDesc();
}

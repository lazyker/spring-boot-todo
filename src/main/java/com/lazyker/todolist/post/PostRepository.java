package com.lazyker.todolist.post;

import com.lazyker.todolist.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findBySubject(String subject);
}

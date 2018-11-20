package com.lazyker.todolist.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(Post post) {
        Post savedPost = this.postRepository.save(post);
        savedPost.initCreatedAt();
        savedPost.setPrefixIdAtSubject();
        this.postRepository.save(savedPost);

        return savedPost;
    }

    public List<Post> findAllPost() {
        return this.postRepository.findAll();
    }

    public Post updatePost(Post post) {
        post.setPrefixIdAtSubject();
        return this.postRepository.save(post);
    }

    public boolean deletePost(Long id) {
        boolean result = true;

        try {
            this.postRepository.delete(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            result = false;

        }

        return result;
    }

    public List<Post> findBySubject(String subject) {
        return this.postRepository.findBySubject(subject);
    }
}

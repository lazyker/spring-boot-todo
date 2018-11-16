package com.lazyker.todolist.post;

import org.springframework.stereotype.Service;

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
}

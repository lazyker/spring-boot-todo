package com.lazyker.todolist.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Test
    public void save() {
        Date start = new Date();

        given(this.postRepository.save(any(Post.class)))
                .willReturn(new Post(1L, "제목", "JPA 어렵다..", null));

        Post savedPost = this.postService.save(new Post("제목", "JPA 어렵다.."));

        assertThat(savedPost.getId()).isEqualTo(1L);
        assertThat(savedPost.getSubject()).isEqualTo("1. 제목");
        assertThat(savedPost.getContent()).isEqualTo("JPA 어렵다..");
        assertThat(savedPost.getCreatedAt()).isBetween(start, new Date());
    }

    @Test
    public void findAllPost() {
        Post post1 = new Post(1L, "제목1", "JPA 어렵다..", new Date());
        Post post2 = new Post(2L, "제목2", "JAVA 이제 못하겠네", new Date());

        given(this.postRepository.findAll())
                .willReturn(asList(post1, post2));

        List<Post> result = this.postService.findAllPost();

        assertThat(result).isEqualTo(asList(post1, post2));
    }

    @Test
    public void updatePost() {
        given(this.postRepository.save(any(Post.class)))
                .willReturn(new Post(1L, "1. 제목_수정", "JPA 어렵다..", new Date()));
        Post savedPost = this.postService.save(new Post("제목_수정", "JPA 어렵다.."));

        assertThat(savedPost.getSubject()).isEqualTo("1. 제목_수정");
    }

    @Test
    public void deletePost() {
        boolean result = this.postService.deletePost(1L);

        assertThat(result).isTrue();
    }

}
package com.lazyker.todolist.post;

import com.lazyker.todolist.common.error.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final ModelMapper modelmapper;

    public PostController(PostService postService, ModelMapper modelmapper) {
        this.postService = postService;
        this.modelmapper = modelmapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post savePost(@RequestBody @Validated PostDto postDto) {
        if (!postDto.getSubject().contains("")) {
            throw new ValidationException("빈 제목은 NO");
        }

//        return this.postService.save(postDto);
        return this.postService.save(this.modelmapper.map(postDto, Post.class));
    }

    @GetMapping
    public List<Post> findAllPost() {
        return this.postService.findAllPost();
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
        postDto.setId(id);
        return this.postService.updatePost(this.modelmapper.map(postDto, Post.class));
    }

    @DeleteMapping("/{id}")
    public boolean deletePost(@PathVariable Long id) {
        return this.postService.deletePost(id);
    }

    @GetMapping("/subjects/{subject}")
    public List<Post> findBySubject(@PathVariable String subject) {
        return this.postService.findBySubject(subject);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    private String validation(ValidationException e) {
        return e.getMessage();
    }
}
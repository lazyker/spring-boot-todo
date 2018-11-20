package com.lazyker.todolist.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @SpyBean
    private ModelMapper modelMapper;

    @Test
    public void savePost() throws Exception {
        given(this.postService.save(any(Post.class)))
                .willReturn(new Post(1L, "1. 제목", "JPA 어렵다..", new Date()));

        this.mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"subject\" : \"제목\", \"content\" : \"JPA 어렵다..\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.subject").value("1. 제목"))
                .andExpect(jsonPath("$.content").value("JPA 어렵다.."))
                .andDo(print());
    }

    @Test
    public void findAllPost() throws Exception {
        Post post1 = new Post(1L, "제목1", "JPA 어렵다..", new Date());
        Post post2 = new Post(2L, "제목2", "JAVA 이제 못하겠네", new Date());

        given(this.postService.findAllPost())
                .willReturn(asList(post1, post2));

        this.mockMvc.perform(get("/posts"))
                .andExpect(jsonPath("$.size()").value(2))
                .andDo(print());
    }

    @Test
    public void updatePost() throws Exception {
        given(this.postService.updatePost(any(Post.class)))
                .willReturn(new Post(1L, "1. 제목", "JPA 어렵다..", new Date()));

        this.mockMvc.perform(put("/posts/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"subject\" : \"제목\", \"content\" : \"JPA 어렵다..\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.subject").value("1. 제목"))
                .andExpect(jsonPath("$.content").value("JPA 어렵다.."))
                .andDo(print());
    }

    @Test
    public void updatePost_subject가_없는_경우() throws Exception {
        this.mockMvc.perform(put("/posts/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"subject\" : \"\", \"content\" : \"JPA 어렵다..\"}"))
                .andExpect(status().isBadRequest());

    }
}

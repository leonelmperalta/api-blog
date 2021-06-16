package com.leonelmperalta.api.blog.controller;

import com.leonelmperalta.api.blog.model.Post;
import com.leonelmperalta.api.blog.service.PostService;
import com.leonelmperalta.api.blog.service.util.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostDTO> getPosts(){
        return postService.getPosts();
    }

    @PostMapping
    public void createPost(@RequestBody Post post){
        postService.createPost(post);
    }
}

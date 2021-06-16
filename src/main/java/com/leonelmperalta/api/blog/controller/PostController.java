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

    //TODO: agregar soporte a parametros titulo y categoria
    @GetMapping
    public List<PostDTO> getPosts(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value= "category", required = false) String category
    ){
        if(title == null && category == null){
            return postService.getPosts();
        } else if (title != null && category != null){
            return postService.getPosts(title, category);
        } else if (title != null){
            return postService.getPostsByTitle(title);
        } else {
            return postService.getPostsByCategory(category);
        }
    }

    @GetMapping(path="/{id}")
    public Post getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @PostMapping
    public void createPost(@RequestBody Post post){
        postService.createPost(post);
    }

    @DeleteMapping(path="/{id}")
    public void deletePost(@PathVariable  Long id){
        postService.deletePost(id);
    }

    @PatchMapping(path = "/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody Post post){
        postService.updatePost(id, post);
    }
}

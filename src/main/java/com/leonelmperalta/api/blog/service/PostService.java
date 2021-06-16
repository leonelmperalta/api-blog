package com.leonelmperalta.api.blog.service;

import com.leonelmperalta.api.blog.model.Category;
import com.leonelmperalta.api.blog.model.Post;
import com.leonelmperalta.api.blog.model.User;
import com.leonelmperalta.api.blog.repository.PostRepository;
import com.leonelmperalta.api.blog.service.util.PostDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<PostDTO> mapToDTO(List<Post> postList){
        List<PostDTO> postsDTO = new ArrayList<PostDTO>();
        for (Post post:
                postList) {
            PostDTO postDTO = new PostDTO();
            modelMapper.map(post,postDTO);
            postsDTO.add(postDTO);
        }
        return postsDTO;
    }

    public List<PostDTO> getPosts() {
        return mapToDTO(postRepository.findAll());
    }

    public void createPost(Post post) {
        postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new IllegalStateException("Post with id: " + id + "not found");
        });
        post.getCategory().deletePost(post);
        post.getUser().deletePost(post);
        post.deleteUser();
        post.deleteCategory();
        postRepository.delete(post);
    }

    public Post getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> {
                    throw new IllegalStateException("Post with id: " + id + "not found");
                }
        );
        return post;
    }

    @Transactional
    public void updatePost(Long id, Post post) {
        Post postToUpdate = postRepository.findById(id).orElseThrow(
                () -> {
                    throw new IllegalStateException("Post with id: " + id + "not found");
                }
        );
        String newTitle = post.getTitle();
        String newContent = post.getContent();
        String newImageUrl = post.getImageUrl();
        LocalDate newCreationDate = post.getCreationDate();
        User newUser = post.getUser();
        Category newCategory = post.getCategory();

        if(newTitle != null && newTitle.length() > 0){
            postToUpdate.setTitle(newTitle);
        }
        if(newContent != null && newContent.length() > 0){
            postToUpdate.setContent(newContent);
        }
        if(newImageUrl != null && newImageUrl.length() > 0){
            postToUpdate.setImageUrl(newImageUrl);
        }
        if(newCreationDate != null){
            postToUpdate.setCreationDate(newCreationDate);
        }
        if(newUser != null){
            postToUpdate.setUser(newUser);
        }
        if(newCategory != null){
            postToUpdate.setCategory(newCategory);
        }
    }
}

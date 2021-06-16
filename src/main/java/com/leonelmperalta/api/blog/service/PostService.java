package com.leonelmperalta.api.blog.service;

import com.leonelmperalta.api.blog.model.Post;
import com.leonelmperalta.api.blog.repository.PostRepository;
import com.leonelmperalta.api.blog.service.util.PostDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //TODO: arreglar este metodo
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
}

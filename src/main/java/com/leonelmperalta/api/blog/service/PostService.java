package com.leonelmperalta.api.blog.service;

import com.leonelmperalta.api.blog.model.Post;
import com.leonelmperalta.api.blog.repository.PostRepository;
import com.leonelmperalta.api.blog.service.util.PostDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}

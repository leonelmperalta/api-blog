package com.leonelmperalta.api.blog.service;

import com.leonelmperalta.api.blog.model.Category;
import com.leonelmperalta.api.blog.model.Post;
import com.leonelmperalta.api.blog.model.User;
import com.leonelmperalta.api.blog.repository.CategoryRepository;
import com.leonelmperalta.api.blog.repository.PostRepository;
import com.leonelmperalta.api.blog.repository.UserRepository;
import com.leonelmperalta.api.blog.service.util.PostDTO;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository, EntityManager entityManager) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.entityManager = entityManager;
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

    public void enableFilter(Session session, boolean isDeleted){
        Filter filter = session.enableFilter("deletedPostFilter");
        filter.setParameter("isDeleted", isDeleted);
    }

    public void disableFilter(Session session){
        session.disableFilter("deletedPostFilter");
    }

    public List<PostDTO> getPosts(){
        Session session = entityManager.unwrap(Session.class);
        enableFilter(session, false);
        List<PostDTO> posts = mapToDTO(postRepository.findAll());
        disableFilter(session);
        return posts;
    }
    public List<PostDTO> getPosts(String title, String category) {
        Session session = entityManager.unwrap(Session.class);
        enableFilter(session, false);
        List<PostDTO> posts = mapToDTO(postRepository.findByTitleAndCategoryName(title, category));
        disableFilter(session);
        return posts;
    }
    public List<PostDTO> getPostsByCategory(String category) {
        Session session = entityManager.unwrap(Session.class);
        enableFilter(session, false);
        List<PostDTO> posts = mapToDTO(postRepository.findByCategoryName(category));
        disableFilter(session);
        return posts;
    }

    public List<PostDTO> getPostsByTitle(String title) {
        Session session = entityManager.unwrap(Session.class);
        enableFilter(session, false);
        List<PostDTO> posts = mapToDTO(postRepository.findByTitle(title));
        disableFilter(session);
        return posts;
    }

    public List<PostDTO> getDeletedPosts() {
        Session session = entityManager.unwrap(Session.class);
        enableFilter(session, true);
        List<PostDTO> posts = mapToDTO(postRepository.findAll());
        disableFilter(session);
        return posts;
    }

    //TODO: Tengo que arreglar que, cuando se crea un post con un usuario no registrado,
    // que el mismo se registre en la BD mediante el userDetailService
    public void createPost(Post post) {
        Optional<User> user = userRepository.findByEmail(post.getUser().getEmail());
        Optional<Category> category = categoryRepository.findByName(post.getCategory().getName());
        user.ifPresent(post::setUser);
        category.ifPresent(post::setCategory);
        postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new IllegalStateException("Post with id: " + id + " not found");
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
                    throw new IllegalStateException("Post with id: " + id + " not found");
                }
        );
        if(post.isDeleted()){
            throw new IllegalStateException("Post with id: " + id + " is deleted");
        }
        return post;
    }

    @Transactional
    public void updatePost(Long id, Post post) {
        Post postToUpdate = postRepository.findById(id).orElseThrow(
                () -> {
                    throw new IllegalStateException("Post with id: " + id + " not found");
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
            Optional<User> user = userRepository.findByEmail(newUser.getEmail());
            if(user.isPresent()){
                postToUpdate.setUser(user.get());
            } else {
                postToUpdate.setUser(newUser);
            }

        }
        if(newCategory != null){
            Optional<Category> category = categoryRepository.findByName(newCategory.getName());
            if(category.isPresent()){
                postToUpdate.setCategory(category.get());
            } else {
                postToUpdate.setCategory(newCategory);
            }
        }
    }

}

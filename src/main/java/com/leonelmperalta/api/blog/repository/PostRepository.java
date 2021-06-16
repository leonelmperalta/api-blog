package com.leonelmperalta.api.blog.repository;

import com.leonelmperalta.api.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitle(String title);
    List<Post> findByCategoryName(String name);
    List<Post> findByTitleAndCategoryName(String title, String name);
}

package com.leonelmperalta.api.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {
    @Id
    @SequenceGenerator(
            name="post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDate creationDate;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    @JsonIgnoreProperties("posts")
    @EqualsAndHashCode.Exclude
    private User user;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("posts")
    @EqualsAndHashCode.Exclude
    private Category category;

    @Transactional
    public void deleteUser(){
        this.user = null;
    }

    @Transactional
    public void deleteCategory(){
        this.category = null;
    }

}

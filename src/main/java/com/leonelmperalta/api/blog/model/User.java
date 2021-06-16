package com.leonelmperalta.api.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Set;

@Entity
@Table(name="users")
@Data
public class User {
    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    @EqualsAndHashCode.Exclude
    private Set<Post> posts;

    @Transactional
    public void deletePost(Post post){
        this.posts.remove(post);
    }
}

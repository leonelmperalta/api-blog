package com.leonelmperalta.api.blog.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "post")
@Data
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
}

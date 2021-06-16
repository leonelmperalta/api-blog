package com.leonelmperalta.api.blog.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="categories")
@Data
public class Category {
    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    private Long id;
    private String name;
}

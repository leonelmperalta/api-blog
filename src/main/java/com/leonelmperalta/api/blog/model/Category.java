package com.leonelmperalta.api.blog.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="categories")
@Data
public class Category {
    @Id
    @SequenceGenerator(
            name = "catogory_sequence",
            sequenceName = "catogory_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    private Long id;
    private String name;
}

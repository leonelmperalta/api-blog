package com.leonelmperalta.api.blog.service.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.leonelmperalta.api.blog.model.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
public class PostDTO {
    private String title;
    private String imageUrl;
    @JsonIgnoreProperties("posts")
    @EqualsAndHashCode.Exclude
    private Category category;
    private LocalDate creationDate;
}

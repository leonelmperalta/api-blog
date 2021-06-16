package com.leonelmperalta.api.blog.service.util;

import com.leonelmperalta.api.blog.model.Category;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PostDTO {
    private String title;
    private String imageUrl;
    private Category category;
    private LocalDate creationDate;
}

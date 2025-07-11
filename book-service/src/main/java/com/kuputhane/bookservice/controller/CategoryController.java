package com.kuputhane.bookservice.controller;

import com.kuputhane.bookservice.model.Category;
import com.kuputhane.bookservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository repo;

    @GetMapping
    public List<Category> all() {
        return repo.findAll();
    }
}

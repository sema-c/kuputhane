package com.kuputhane.bookservice.controller;

import com.kuputhane.bookservice.model.Publisher;
import com.kuputhane.bookservice.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherRepository repo;

    @GetMapping
    public List<Publisher> all() {
        return repo.findAll();
    }
}


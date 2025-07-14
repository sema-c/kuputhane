package com.kuputhane.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kuputhane.bookservice.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> { }


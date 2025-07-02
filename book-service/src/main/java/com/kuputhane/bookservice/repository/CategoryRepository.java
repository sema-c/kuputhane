package com.kuputhane.bookservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import com.kuputhane.bookservice.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query("SELECT c.name FROM Category c WHERE c.id = :id")
    String findCategoryNameById(@Param("id") Long id);
}

package com.kuputhane.bookservice.repository;

import com.kuputhane.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT c.name FROM Category c WHERE c.id = :id")
    String findCategoryNameById(@Param("id") Long id);

    @Query("SELECT p.name FROM Publisher p WHERE p.id = :id")
    String findPublisherNameById(@Param("id") Long id);
}

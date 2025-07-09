package com.kuputhane.bookservice.repository;

import com.kuputhane.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByDueDateBeforeAndReturnedFalse(LocalDate date);

    @Query(value = "SELECT name FROM categories WHERE id = :id", nativeQuery = true)
    String findCategoryNameById(@Param("id") Long id);

    @Query(value = "SELECT name FROM publishers WHERE id = :id", nativeQuery = true)
    String findPublisherNameById(@Param("id") Long id);

}

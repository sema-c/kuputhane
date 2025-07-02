package com.kuputhane.bookservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import com.kuputhane.bookservice.model.Publisher;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {

    @Query("SELECT p.name FROM Publisher p WHERE p.id = :id")
    String findPublisherNameById(@Param("id") Long id);
}

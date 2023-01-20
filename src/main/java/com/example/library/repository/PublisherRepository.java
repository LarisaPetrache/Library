package com.example.library.repository;

import com.example.library.models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
    Publisher findByPublisherId(Integer id);

    Publisher findByName(String name);
}

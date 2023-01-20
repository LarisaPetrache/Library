package com.example.library.service;

import com.example.library.exception.PublisherAlreadyExistException;
import com.example.library.models.Member;
import com.example.library.models.Publisher;
import com.example.library.repository.PublisherRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher findPublisherById(Integer id){
        return publisherRepository.findByPublisherId(id);
    }

    @Transactional
    @Modifying
    public void updatePublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public Publisher findByName(String name) {
        return publisherRepository.findByName(name);
    }
}

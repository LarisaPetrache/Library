package com.example.library.service;

import com.example.library.models.Publisher;
import com.example.library.repository.PublisherRepository;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher savePublisher(Publisher publisher){
        return publisherRepository.save(publisher);
    }
}

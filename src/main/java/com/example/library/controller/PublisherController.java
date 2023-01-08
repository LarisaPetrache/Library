package com.example.library.controller;

import com.example.library.models.Publisher;
import com.example.library.service.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/new")
    public ResponseEntity<Publisher> savePublisher(@RequestBody Publisher publisher){
        return ResponseEntity.ok()
                .body(publisherService.savePublisher(publisher));
    }
}

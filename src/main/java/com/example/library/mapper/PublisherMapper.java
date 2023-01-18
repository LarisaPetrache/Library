package com.example.library.mapper;

import com.example.library.dto.PublisherRequest;
import com.example.library.models.Publisher;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {
    public Publisher publisherRequestToPublisher(PublisherRequest publisherRequest){
        return new Publisher(publisherRequest.getName(), publisherRequest.getPhoneNumber());
    }
}

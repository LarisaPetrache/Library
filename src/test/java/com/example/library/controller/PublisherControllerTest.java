package com.example.library.controller;

import com.example.library.dto.PublisherRequest;
import com.example.library.exception.PublisherAlreadyExistException;
import com.example.library.mapper.PublisherMapper;
import com.example.library.models.Publisher;
import com.example.library.service.PublisherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublisherControllerTest {

    @InjectMocks
    private PublisherController publisherController;

    @Mock
    private PublisherService publisherService;

    @Mock
    private PublisherMapper publisherMapper;

    @Test
    public void addNewPublisher_savePublisher() throws PublisherAlreadyExistException {
        PublisherRequest publisherRequest = new PublisherRequest();
        publisherRequest.setName("Publisher Name");
        publisherRequest.setPhoneNumber("0734198345");

        Publisher publisher = new Publisher(1, "Publisher Name", "0734198345");

        // No publisher found with the name provided in the database
        when(publisherService.findByName(publisherRequest.getName())).thenReturn(null);

        when(publisherMapper.publisherRequestToPublisher(any(PublisherRequest.class))).thenReturn(publisher);
        when(publisherService.savePublisher(publisher)).thenReturn(publisher);

        ResponseEntity<Publisher> response = publisherController.savePublisher(publisherRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/publisher/1", Objects.requireNonNull(response.getHeaders().getLocation()).getPath());
        assertEquals(publisher, response.getBody());
    }

    @Test
    public void publisherAlreadyExist_create_throwsPublisherAlreadyExistException() {
        PublisherRequest publisherRequest = new PublisherRequest();
        publisherRequest.setName("Publisher2");
        publisherRequest.setPhoneNumber("0723472895");

        when(publisherService.findByName(publisherRequest.getName())).thenReturn(new Publisher());

        try {
            publisherController.savePublisher(publisherRequest);
            fail();
        } catch (PublisherAlreadyExistException e) {
            assertEquals("This publisher already exist!", e.getMessage());
        }
    }

    @Test
    public void getAllPublishers() throws Exception {
        List<Publisher> publishers = Arrays.asList(
                new Publisher("Publisher1", "0723671942"),
                new Publisher("Publisher2", "0734172954"),
                new Publisher("Publisher3", "0723456983")
        );

        when(publisherService.findAllPublishers()).thenReturn(publishers);

        assertEquals(HttpStatus.OK, publisherController.getAllPublishers().getStatusCode());
        verify(publisherService, times(1)).findAllPublishers();
    }

    @Test
    public void idExist_getPublisherById() {
        int id = 1;
        Publisher publisher = new Publisher("Publisher1", "0722349864");

        when(publisherService.findPublisherById(id)).thenReturn(publisher);

        ResponseEntity<?> responseEntity = publisherController.getPublisherById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(publisher, responseEntity.getBody());

        verify(publisherService, times(1)).findPublisherById(id);
    }

    @Test
    public void idDoesntExist_getPublisherById(){
        int id = 1;
        when(publisherService.findPublisherById(id)).thenReturn(null);

        ResponseEntity<?> responseEntity = publisherController.getPublisherById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(publisherService, times(1)).findPublisherById(id);
    }

}

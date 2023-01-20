package com.example.library.controller;

import com.example.library.dto.PublisherRequest;
import com.example.library.exception.GenreAlreadyExistException;
import com.example.library.exception.PublisherAlreadyExistException;
import com.example.library.mapper.PublisherMapper;
import com.example.library.models.Publisher;
import com.example.library.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/publisher")
@Validated
@Tag(name = "✦ Publisher Controller", description = "/publisher")
public class PublisherController {

    private final PublisherService publisherService;
    private final PublisherMapper publisherMapper;

    public PublisherController(PublisherService publisherService, PublisherMapper publisherMapper) {
        this.publisherService = publisherService;
        this.publisherMapper = publisherMapper;
    }

    @Operation(summary = "Add a new publisher",
            description = "Adds a new publisher based on the information received in the request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "The publisher was successfully added based on the received request"),
            @ApiResponse(responseCode = "400", description = "Validation error on the received request")
    })
    @Parameter(name = "publisherRequest", description = "Contains publisher details", required = true)
    /* ====================
          Save publisher
    ======================= */
    @PostMapping
    public ResponseEntity<Publisher> savePublisher(@RequestBody @Valid PublisherRequest publisherRequest)
            throws PublisherAlreadyExistException {

        if(publisherService.findByName(publisherRequest.getName()) != null){
            throw new PublisherAlreadyExistException();
        }

        Publisher publisher = publisherService.savePublisher(
                publisherMapper.publisherRequestToPublisher(publisherRequest));

        return ResponseEntity.created(URI.create("/publisher/" + publisher.getPublisherId()))
                .body(publisher);
    }

    @Operation(summary = "Get all publishers")
    /* =======================
          Get all publishers
    ========================== */
    @GetMapping("/list")
    public ResponseEntity<List<Publisher>> getAllPublishers(){
        return ResponseEntity.ok().body(publisherService.findAllPublishers());
    }

    @Operation(summary = "Get publisher by ID")
    /* =======================
        Get publisher by ID
    ========================== */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPublisherById(@PathVariable int id) {
        Publisher publisher = publisherService.findPublisherById(id);

        if (publisher == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .body(publisher);
    }
}

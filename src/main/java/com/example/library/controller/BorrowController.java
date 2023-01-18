package com.example.library.controller;

import com.example.library.dto.BorrowRequest;
import com.example.library.mapper.BorrowMapper;
import com.example.library.models.Borrow;
import com.example.library.service.BorrowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrow")
@Validated
@Tag(name = "✦ Borrow Records Controller", description = "/borrow")
public class BorrowController {

    private final BorrowService borrowService;
    private final BorrowMapper borrowMapper;

    public BorrowController(BorrowService borrowService, BorrowMapper borrowMapper) {
        this.borrowService = borrowService;
        this.borrowMapper = borrowMapper;
    }

    @Operation(summary = "Create a borrow record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "The borrow record was successfully created based on the received request"),
            @ApiResponse(responseCode = "400", description = "Validation error on the received request")
    })
    @Parameter(name = "borrowRequest", description = "Borrow details", required = true)
    @Parameter(name = "bookId", description = "Book ID", required = true)
    @Parameter(name = "memberId", description = "Member ID", required = true)
    /* ========================
         Save a Borrow record
     ========================== */
    @PostMapping
    public ResponseEntity<Borrow> saveBorrow(@RequestBody @Valid BorrowRequest borrowRequest,
                                            @RequestParam int bookId,
                                            @RequestParam int memberId){
        Borrow borrow = borrowMapper.borrowRequestToBorrow(borrowRequest);

        return ResponseEntity.ok()
                .body(borrowService.saveBorrow(borrow, bookId, memberId));
    }

    @Operation(summary = "Get all borrow records for a specified member")
    /* ==========================
        Get all borrow records
     ============================ */
    @GetMapping("/records")
    public ResponseEntity<?> retrieveBorrowRecords(@RequestParam int memberId){
        List<Borrow> borrowRecords = borrowService.findAllBorrowRecordsForMember(memberId);

        if(borrowRecords.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(borrowRecords);
    }
}

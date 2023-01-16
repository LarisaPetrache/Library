package com.example.library.controller;

import com.example.library.models.Borrow;
import com.example.library.service.BorrowService;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping("/new")
    public ResponseEntity<Borrow> saveBorrow(@RequestBody Borrow borrow,
                                            @RequestParam int bookId,
                                            @RequestParam int memberId){
        return ResponseEntity.ok()
                .body(borrowService.saveBorrow(borrow, bookId, memberId));
    }
}

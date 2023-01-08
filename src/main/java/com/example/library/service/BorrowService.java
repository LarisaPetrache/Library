package com.example.library.service;

import com.example.library.repository.BorrowRepository;
import org.springframework.stereotype.Service;

@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;

    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }
}

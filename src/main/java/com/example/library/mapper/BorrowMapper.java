package com.example.library.mapper;

import com.example.library.dto.BorrowRequest;
import com.example.library.models.Borrow;
import org.springframework.stereotype.Component;

@Component
public class BorrowMapper {
    public Borrow borrowRequestToBorrow(BorrowRequest borrowRequest){
        return new Borrow(borrowRequest.getBorrowDate(), borrowRequest.getDueDate(),
                borrowRequest.getReturnDate());
    }
}

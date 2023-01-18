package com.example.library.service;

import com.example.library.models.Book;
import com.example.library.models.Borrow;
import com.example.library.models.Member;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowRepository;
import com.example.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public BorrowService(BorrowRepository borrowRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public Borrow saveBorrow(Borrow borrow, int bookId, int memberId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book id is not valid"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member id is not valid"));

        borrow.setBook(book);
        borrow.setMember(member);

        return borrowRepository.save(borrow);
    }

    public List<Borrow> findAllBorrowRecordsForMember(int memberId) {
        return borrowRepository.findAllByMember_MemberId(memberId);
    }
}

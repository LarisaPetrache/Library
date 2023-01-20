package com.example.library.service;

import com.example.library.exception.MemberEmailAlreadyExistException;
import com.example.library.models.Member;
import com.example.library.repository.MemberRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member findMemberById(Integer id) {
        return memberRepository.findByMemberId(id);
    }

    @Transactional
    @Modifying
    public void updateMember(Member member) {
        memberRepository.save(member);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}

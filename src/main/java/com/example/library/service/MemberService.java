package com.example.library.service;

import com.example.library.models.Member;
import com.example.library.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member saveMember(Member member){
        return memberRepository.save(member);
    }
}

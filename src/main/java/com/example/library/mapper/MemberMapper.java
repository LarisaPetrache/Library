package com.example.library.mapper;

import com.example.library.dto.MemberRequest;
import com.example.library.models.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member memberRequestToMember(MemberRequest memberRequest){
        return new Member(memberRequest.getFirstName(), memberRequest.getLastName(),
                memberRequest.getPhoneNumber(), memberRequest.getEmail(),
                memberRequest.getAddress(), memberRequest.getMembershipStartDate(),
                memberRequest.getMembershipEndDate());
    }
}

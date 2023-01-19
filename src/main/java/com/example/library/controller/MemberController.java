package com.example.library.controller;

import com.example.library.dto.MemberRequest;
import com.example.library.mapper.MemberMapper;
import com.example.library.models.Genre;
import com.example.library.models.Member;
import com.example.library.service.MemberService;
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
@RequestMapping("/member")
@Validated
@Tag(name = "✦ Member Controller", description = "/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    @Operation(summary = "Add a new member",
            description = "Adds a new member based on the information received in the request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "The member was successfully added based on the received request"),
            @ApiResponse(responseCode = "400", description = "Validation error on the received request")
    })
    @Parameter(name = "memberRequest", description = "Contains member details", required = true)
    /* ==================
          Save member
    ===================== */
    @PostMapping
    public ResponseEntity<Member> saveMember(@RequestBody @Valid MemberRequest memberRequest){

        Member member = memberService.saveMember(memberMapper.memberRequestToMember(memberRequest));

        return ResponseEntity.created(URI.create("/member/" + member.getMemberId()))
                .body(member);
    }

    @Operation(summary = "Get all members")
    /* ====================
          Get all members
    ======================= */
    @GetMapping("/list")
    public ResponseEntity<List<Member>> getAllMembers(){
        return ResponseEntity.ok().body(memberService.findAllMembers());
    }

    @Operation(summary = "Get member by ID")
    /* =====================
          Get member by ID
    ======================== */
    @GetMapping("/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable int id) {
        Member member = memberService.findMemberById(id);

        if (member == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .body(member);
    }

    @Operation(summary = "Update member's fields provided by user")
    /* =========================
        Update member's fields
    ============================ */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGenre(@RequestBody MemberRequest memberRequest, @PathVariable Integer id){

        Member updateMember = memberMapper.memberRequestToMember(memberRequest);
        Member member = memberService.findMemberById(id);

        // Update the non-null attributes from updateMember
        if(updateMember.getFirstName() != null)
            member.setFirstName(updateMember.getFirstName());

        if(updateMember.getLastName() != null)
            member.setLastName(updateMember.getLastName());

        if(updateMember.getAddress() != null)
            member.setAddress(updateMember.getAddress());

        if(updateMember.getEmail() != null)
            member.setEmail(updateMember.getEmail());

        if(updateMember.getMembershipStartDate() != null)
            member.setMembershipStartDate(updateMember.getMembershipStartDate());

        if(updateMember.getMembershipEndDate() != null)
            member.setMembershipEndDate(updateMember.getMembershipEndDate());

        if(updateMember.getPhoneNumber() != null)
            member.setPhoneNumber(updateMember.getPhoneNumber());

        memberService.updateMember(member);

        return ResponseEntity.ok().body("Member fields updated!");
    }

}

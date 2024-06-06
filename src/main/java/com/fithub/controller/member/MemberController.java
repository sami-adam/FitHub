package com.fithub.controller.member;

import com.fithub.dto.member.MemberDTO;
import com.fithub.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class MemberController {
    private MemberService memberService;

    @Autowired
    public void setMemberService(MemberService memberService){
        this.memberService = memberService;
    }

    // Get Members
    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> getMembers(){
        return new ResponseEntity<>(memberService.getMembers(), HttpStatus.OK);
    }

    // Add New Member
    @PostMapping("/member")
    public ResponseEntity<MemberDTO> addMember(@RequestBody MemberDTO memberDTO){
        return new ResponseEntity<>(memberService.addMember(memberDTO), HttpStatus.CREATED);
    }

    // Update Member
    @PutMapping("/member")
    public ResponseEntity<MemberDTO> updateMember(@RequestBody MemberDTO memberDTO){
        return new ResponseEntity<>(memberService.updateMember(memberDTO), HttpStatus.OK);
    }

    // Delete Member
    @DeleteMapping("/member/{id}")
    public ResponseEntity<Map<String, String>> deleteMember(@PathVariable("id") Long id){
        return new ResponseEntity<>(memberService.deleteMember(id), HttpStatus.OK);
    }

    // Search Members
    @GetMapping("/members/keyword/{keyword}")
    public ResponseEntity<List<MemberDTO>> searchMembers(@PathVariable("keyword") String keyword){
        return new ResponseEntity<>(memberService.searchMembers(keyword), HttpStatus.OK);
    }
}

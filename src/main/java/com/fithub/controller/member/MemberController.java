package com.fithub.controller.member;

import com.fithub.dto.base.AttachmentDTO;
import com.fithub.dto.member.MemberDTO;
import com.fithub.model.base.AttachmentType;
import com.fithub.model.base.ResponseModel;
import com.fithub.service.base.AttachmentService;
import com.fithub.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class MemberController {
    private final MemberService memberService;
    private final AttachmentService attachmentService;

    public MemberController(MemberService memberService, AttachmentService attachmentService) {
        this.memberService = memberService;
        this.attachmentService = attachmentService;
    }

    // Get Members
    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> getMembers(){
        return new ResponseEntity<>(memberService.getMembers(), HttpStatus.OK);
    }

    // Get Member
    @GetMapping("/member/{id}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable("id") Long id){
        return new ResponseEntity<>(memberService.getMember(id), HttpStatus.OK);
    }

    // Add New Member
    @PostMapping("/member")
    public ResponseEntity<MemberDTO> addMember(@RequestBody MemberDTO memberDTO){
        return new ResponseEntity<>(memberService.addMember(memberDTO), HttpStatus.CREATED);
    }

    // Update Member
    @PutMapping("/member/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable("id") Long id,@RequestBody MemberDTO memberDTO){
        return new ResponseEntity<>(memberService.updateMember(id, memberDTO), HttpStatus.OK);
    }

    // Delete Member
    @DeleteMapping("/member/{id}")
    public ResponseEntity<Map<String, String>> deleteMember(@PathVariable("id") Long id){
        return new ResponseEntity<>(memberService.deleteMember(id), HttpStatus.OK);
    }

    // Search Members
    @GetMapping("/members/search/{keyword}")
    public ResponseEntity<List<MemberDTO>> searchMembers(@PathVariable("keyword") String keyword){
        return new ResponseEntity<>(memberService.searchMembers(keyword), HttpStatus.OK);
    }

    // My Profile
    @GetMapping("/member/profile")
    public ResponseModel<MemberDTO> getMyProfile(@RequestHeader("Authorization") String token){
        return new ResponseModel<>(true, memberService.getMyProfile(token), "My Profile");
    }

    // Upload Profile Picture
    @PostMapping("/member/{id}/picture")
    public ResponseEntity<MemberDTO> uploadProfilePicture(@PathVariable("id") Long id, @RequestParam("attachment") MultipartFile picture) throws IOException {
        AttachmentDTO attachmentDTO = attachmentService.storeAttachmentByContent(picture);
        return new ResponseEntity<>(memberService.uploadProfilePicture(id, attachmentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/member/{id}/picture")
    public ResponseEntity<Map<String, String>> deleteProfilePicture(@PathVariable("id") Long id){
        return new ResponseEntity<>(memberService.deleteProfilePicture(id), HttpStatus.OK);
    }
}

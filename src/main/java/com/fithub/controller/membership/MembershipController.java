package com.fithub.controller.membership;

import com.fithub.dto.membership.MembershipDTO;
import com.fithub.service.membership.MembershipService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Data
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class MembershipController {
    private MembershipService membershipService;
    @Autowired
    public void setMembershipService(MembershipService membershipService){
        this.membershipService = membershipService;
    }

    // Get All Memberships
    @GetMapping(path = "/memberships", produces = {"Application/json"})
    public ResponseEntity<List<MembershipDTO>> getMemberships(){
        return new ResponseEntity<>(membershipService.getMemberships(), HttpStatus.OK);
    }

    @GetMapping("/membership/{id}")
    public ResponseEntity<MembershipDTO> getMembership(@PathVariable("id") Long id){
        return new ResponseEntity<>(membershipService.getMembership(id), HttpStatus.OK);
    }

    // Add New Membership
    @PostMapping("membership")
    public ResponseEntity<MembershipDTO> addMembership(@RequestBody MembershipDTO membershipDTO){
        return new ResponseEntity<>(membershipService.addMembership(membershipDTO), HttpStatus.CREATED);
    }

    // Update Membership
    @PutMapping("membership")
    public ResponseEntity<MembershipDTO> updateMembership(@RequestBody MembershipDTO membershipDTO){
        return new ResponseEntity<>(membershipService.updateMembership(membershipDTO), HttpStatus.OK);
    }

    // Delete Membership
    @DeleteMapping("membership/{id}")
    public ResponseEntity<Map<String, String>> deleteMembership(@PathVariable("id") Long id){
        return new ResponseEntity<>(membershipService.deleteMembership(id), HttpStatus.OK);
    }

    // Search Memberships
    @GetMapping("memberships/keyword/{keyword}")
    public ResponseEntity<List<MembershipDTO>> searchMembership(@PathVariable("keyword") String keyword){
        return new ResponseEntity<>(membershipService.searchMemberShip(keyword), HttpStatus.OK);
    }
}

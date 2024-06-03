package com.fithub.controller.membership;

import com.fithub.dto.membership.MembershipDTO;
import com.fithub.service.membership.MembershipService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping(path = "/memberships", produces = {"Application/json"})
    public ResponseEntity<List<MembershipDTO>> getMemberships(){
        return new ResponseEntity<>(membershipService.getMemberships(), HttpStatus.OK);
    }

    @PostMapping("membership")
    public ResponseEntity<MembershipDTO> addMembership(@RequestBody MembershipDTO membershipDTO){
        return new ResponseEntity<>(membershipService.addMembership(membershipDTO), HttpStatus.OK);
    }
}

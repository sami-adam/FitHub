package com.fithub.service.membership;

import com.fithub.dto.membership.MembershipDTO;
import com.fithub.dto.subscription.SubscriptionDTO;
import com.fithub.model.membership.Membership;
import com.fithub.model.subscription.Subscription;
import com.fithub.repository.membership.MembershipRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Data
public class MembershipServiceImpl implements MembershipService{
    private final MembershipRepository membershipRepository;
    private final ModelMapper mapper;
    @Autowired
    public MembershipServiceImpl(MembershipRepository membershipRepository){
        this.membershipRepository = membershipRepository;
        this.mapper = new ModelMapper();
    }

    // Get All Memberships
    public List<MembershipDTO> getMemberships(){
        List<Membership> memberships = membershipRepository.findAll().stream().toList();
        return memberships.stream().map(membership -> mapper.map(membership, MembershipDTO.class)).toList();
    }

    @Override
    public MembershipDTO getMembership(Long id) {
        Membership membership = membershipRepository.findById(id).orElseThrow();
        if(membership.getId() > 0){
            return mapper.map(membership, MembershipDTO.class);
        }
        return null;
    }

    // Add New Membership
    public MembershipDTO addMembership(MembershipDTO membershipDTO) {
        System.out.println(membershipDTO);
        membershipRepository.save(mapper.map(membershipDTO, Membership.class));
        return membershipDTO;
    }

    // Update Membership
    public MembershipDTO updateMembership(MembershipDTO membershipDTO) {
        Membership membership = membershipRepository.save(mapper.map(membershipDTO, Membership.class));
        return mapper.map(membership, MembershipDTO.class);
    }

    // Delete Membership
    public Map<String, String> deleteMembership(Long id){
        Map<String, String> response = new HashMap<>();
        membershipRepository.deleteById(id);
        response.put("message", "Resource has been deleted");
        response.put("status", "successful");
        return response;
    }

    // Search Memberships
    public List<MembershipDTO> searchMemberShip(String keyword){
        List<Membership> memberships = membershipRepository.searchByKeyword(keyword, keyword, keyword);
        return memberships.stream().map(membership -> mapper.map(membership, MembershipDTO.class)).toList();
    }

}

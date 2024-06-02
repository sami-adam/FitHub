package com.fithub.service.membership;

import com.fithub.dto.membership.MembershipDTO;
import com.fithub.model.membership.Membership;
import com.fithub.repository.membership.MembershipRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class MembershipServiceImpl implements MembershipService{
    private MembershipRepository membershipRepository;
    private ModelMapper mapper;
    @Autowired
    public void setMembershipRepository(MembershipRepository membershipRepository){
        this.membershipRepository = membershipRepository;
        this.mapper = new ModelMapper();
    }

    public List<MembershipDTO> getMemberships(){
        List<Membership> memberships = membershipRepository.findAll().stream().toList();
        return memberships.stream().map(membership -> mapper.map(membership, MembershipDTO.class)).toList();
    }
}

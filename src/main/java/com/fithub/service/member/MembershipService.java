package com.fithub.service.member;

import com.fithub.dto.member.MembershipDTO;

import java.util.List;
import java.util.Map;

public interface MembershipService {
    List<MembershipDTO> getMemberships();
    MembershipDTO addMembership(MembershipDTO membershipDTO);
    MembershipDTO updateMembership(Long id, MembershipDTO membershipDTO);
    Map<String, String> deleteMembership(Long id);
    List<MembershipDTO> searchMemberships(String keyword);
}

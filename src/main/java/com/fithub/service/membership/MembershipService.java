package com.fithub.service.membership;

import com.fithub.dto.membership.MembershipDTO;

import java.util.List;
import java.util.Map;

public interface MembershipService {
    List<MembershipDTO> getMemberships();
    MembershipDTO getMembership(Long id);
    MembershipDTO addMembership(MembershipDTO membershipDTO);
    MembershipDTO updateMembership(MembershipDTO membershipDTO);
    Map<String, String> deleteMembership(Long id);
    List<MembershipDTO> searchMemberShip(String keyword);
}

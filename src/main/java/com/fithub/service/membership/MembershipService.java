package com.fithub.service.membership;

import com.fithub.dto.membership.MembershipDTO;

import java.util.List;

public interface MembershipService {
    List<MembershipDTO> getMemberships();
    MembershipDTO addMembership(MembershipDTO membershipDTO);
}

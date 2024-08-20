package com.fithub.service.member;

import com.fithub.dto.base.AttachmentDTO;
import com.fithub.dto.member.MemberDTO;

import java.util.List;
import java.util.Map;

public interface MemberService {
    List<MemberDTO> getMembers();
    MemberDTO addMember(MemberDTO membershipDTO);
    MemberDTO updateMember(Long id, MemberDTO membershipDTO);
    Map<String, String> deleteMember(Long id);
    List<MemberDTO> searchMembers(String keyword);
    MemberDTO getMyProfile(String token);
    MemberDTO uploadProfilePicture(Long id, AttachmentDTO picture);
    Map<String, String> deleteProfilePicture(Long id);
    MemberDTO findMemberByEmail(String email);

}

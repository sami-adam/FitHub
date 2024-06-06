package com.fithub.service.member;

import com.fithub.dto.member.MemberDTO;
import com.fithub.model.member.Member;
import com.fithub.repository.member.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
        this.mapper = new ModelMapper();
    }

    // Get All Members
    public List<MemberDTO> getMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(member -> mapper.map(member, MemberDTO.class)).toList();
    }

    // Add New Member
    public MemberDTO addMember(MemberDTO membershipDTO) {
        Member member = memberRepository.save(mapper.map(membershipDTO, Member.class));
        return mapper.map(member, MemberDTO.class);
    }

    // Update Member
    public MemberDTO updateMember(MemberDTO membershipDTO) {
        Member member = memberRepository.save(mapper.map(membershipDTO, Member.class));
        return mapper.map(member, MemberDTO.class);
    }

    // Delete Member
    public Map<String, String> deleteMember(Long id) {
        Map<String, String> response = new HashMap<>();
        memberRepository.deleteById(id);
        response.put("message", "Resource has been successfully deleted");
        response.put("status", "success");
        return  response;
    }

    // Search Members
    public List<MemberDTO> searchMembers(String keyword) {
        List<Member> members = memberRepository.searchByFirstNameContainingOrLastNameContainingOrEmailContaining(keyword, keyword, keyword);
        return members.stream().map(member -> mapper.map(member, MemberDTO.class)).toList();
    }
}

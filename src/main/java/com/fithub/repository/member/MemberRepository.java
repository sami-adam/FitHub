package com.fithub.repository.member;

import com.fithub.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> searchByFirstNameContainingOrLastNameContainingOrEmailContaining(String firstName, String lastName, String email);
}

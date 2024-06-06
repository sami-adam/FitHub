package com.fithub.repository.membership;

import com.fithub.model.membership.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    @Query("select m from Membership as m where m.member.firstName like %:firstName% or m.member.lastName like %:lastName% or m.subscription.name like %:subscription%")
    List<Membership> searchByKeyword(String firstName, String lastName, String subscription);
}

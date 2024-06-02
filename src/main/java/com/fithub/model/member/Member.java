package com.fithub.model.member;

import com.fithub.model.User;
import com.fithub.model.address.Address;
import com.fithub.model.membership.Membership;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "members")
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "first_name")
    private String firstName;

    @JoinColumn(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Gender gender;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Email
    @JoinColumn(unique = true, nullable = false)
    private String email;

    private String Phone;

    @OneToMany(orphanRemoval = true, mappedBy = "member")
    private List<Membership> memberships;


}

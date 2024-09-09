package com.fithub.model.member;

import com.fithub.model.base.Attachment;
import com.fithub.model.user.User;
import com.fithub.model.address.Address;
import com.fithub.model.base.BaseEntity;
import com.fithub.model.subscription.Subscription;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "members")
@Data
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "identification_number")
    @Column(unique = true, nullable = false)
    private String identificationNumber;

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
    private List<Subscription> subscriptions;

    private MemberStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_picture_id")
    private Attachment profilePicture;


}

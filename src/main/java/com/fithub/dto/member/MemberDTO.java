package com.fithub.dto.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fithub.dto.address.AddressDTO;
import com.fithub.dto.membership.MembershipDTO;
import com.fithub.model.User;
import com.fithub.model.member.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class MemberDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private User user;
    private Gender gender;
    private AddressDTO address;
    @Email
    @NotNull
    private String email;
    private String Phone;
    private List<MembershipDTO> memberShips;


}

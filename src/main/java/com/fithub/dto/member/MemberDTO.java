package com.fithub.dto.member;

import com.fithub.dto.address.AddressDTO;
import com.fithub.dto.base.BaseEntityDTO;
import com.fithub.dto.subscription.SubscriptionDTO;
import com.fithub.model.member.MemberStatus;
import com.fithub.model.user.User;
import com.fithub.model.member.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class MemberDTO extends BaseEntityDTO {
    private Long id;

    @NotNull(message = "Identification number cannot be null")
    private String identificationNumber;
    @NotNull(message = "First name cannot be null")
    @Size(min = 2, message = "First name must be at least 2 characters long")
    private String firstName;
    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, message = "Last name must be at least 2 characters long")
    private String lastName;
    private User user;
    private Gender gender;
    private AddressDTO address;
    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    private String email;
    private String Phone;
    private List<SubscriptionDTO> subscriptions;
    private MemberStatus status;


}

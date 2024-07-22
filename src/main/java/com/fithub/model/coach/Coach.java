package com.fithub.model.coach;

import com.fithub.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "coaches")
@Data
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identification_number")
    private String identificationNumber;
    private String name;
    private String email;
    private String phone;
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

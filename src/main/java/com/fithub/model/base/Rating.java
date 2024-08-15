package com.fithub.model.base;

import com.fithub.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating")
    @Min(1)
    @Max(5)
    private int rating;

    private String comment;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

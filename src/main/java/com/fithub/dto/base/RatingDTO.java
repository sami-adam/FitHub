package com.fithub.dto.base;

import com.fithub.dto.user.UserDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RatingDTO {
    private Long id;
    private int rating;
    private String comment;
    private LocalDate date;
    private UserDTO user;
}

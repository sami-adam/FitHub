package com.fithub.dto.fitnessClass;
import lombok.Data;

import java.util.List;

@Data
public class FitnessClassDTO {

    private Long id;
    private String name;
    private String description;
    private String intensityLevel;
    private String images;
}

package com.fithub.dto.fitnessClass;
import com.fithub.dto.base.AttachmentDTO;
import com.fithub.dto.base.BaseEntityDTO;
import lombok.Data;

import java.util.List;

@Data
public class FitnessClassDTO extends BaseEntityDTO {

    private Long id;
    private String name;
    private String description;
    private String intensityLevel;
    private List<AttachmentDTO> images;
}

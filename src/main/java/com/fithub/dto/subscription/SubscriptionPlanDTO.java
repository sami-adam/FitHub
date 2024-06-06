package com.fithub.dto.subscription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class SubscriptionPlanDTO {
    private Long id;
    private String name;
}

package com.fithub.dto.fitnessClass;

import com.fithub.dto.base.TaxDTO;
import com.fithub.dto.member.MemberDTO;
import com.fithub.model.fitnessClass.ClassEnrollment;
import lombok.Data;

import java.util.Date;

@Data
public class ClassEnrollmentDTO {
    private  Long id;
    private String reference;
    private MemberDTO member;
    private FitnessClassDTO fitnessClass;
    private ClassScheduleDTO classSchedule;
    private Date startDate;
    private Date endDate;
    private double price;
    private double discountAmount;
    private double taxAmount;
    private double netAmount;
    private TaxDTO tax;
    private ClassEnrollment.Status status;
}

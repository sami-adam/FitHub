package com.fithub.model.fitnessClass;

import com.fithub.model.base.BaseEntity;
import com.fithub.model.base.Tax;
import com.fithub.model.subscription.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@Table(name = "class_enrollments")
public class ClassEnrollment extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private  Long id;

    private String reference;

    @ManyToOne
    @JoinColumn(name = "fitness_class_id")
    private FitnessClass fitnessClass;

    @ManyToOne
    @JoinColumn(name = "class_schedule_id")
    private ClassSchedule classSchedule;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private Date endDate;

    private double price;

    @Column(name = "discount_amount")
    private double discountAmount;

    @Column(name = "tax_amount")
    private double taxAmount;

    @Column(name = "net_amount")
    private double netAmount;

    @ManyToOne
    @JoinColumn(name = "tax_id")
    private Tax tax;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        NEW, PAID, ACTIVE, EXPIRED, CANCELLED
    }

    @PostPersist
    public void postPersist() {
        reference = "CET" + String.format("%06d", id);
        status = Status.NEW;
        tax = fitnessClass.getTax();
        price = classSchedule.getPrice() != null ? classSchedule.getPrice() : 0.0;
        taxAmount = tax != null ? price * tax.getRate()/100 : 0.0;
        netAmount = price + taxAmount - discountAmount;
    }

    @PreUpdate
    public void preUpdate() {
        if(status == null){
            status = Status.NEW;
        }
        tax = fitnessClass.getTax();
        // To Be Removed
        price = classSchedule.getPrice();
        taxAmount = tax != null ? price * tax.getRate()/100 : 0.0;
        netAmount = price + taxAmount - discountAmount;
    }
}

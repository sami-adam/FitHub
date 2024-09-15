package com.fithub.model.fitnessClass;

import com.fithub.model.base.BaseEntity;
import com.fithub.model.base.Tax;
import com.fithub.model.member.Member;
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
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "fitness_class_id", nullable = false)
    private FitnessClass fitnessClass;

    @ManyToOne
    @JoinColumn(name = "class_schedule_id", nullable = false)
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

    @PrePersist
    public void prePersist() {
        this.status = Status.NEW;
        this.startDate = this.classSchedule.getStartDate();
        this.endDate = this.classSchedule.getEndDate();
        this.price = this.classSchedule.getPrice() != null ? classSchedule.getPrice() : 0.0;
        this.taxAmount = this.tax != null ? this.price * this.tax.getRate()/100 : 0.0;
        this.netAmount = this.price + this.taxAmount - this.discountAmount;
    }

    @PostPersist
    public void postPersist() {
        this.reference = "CET" + String.format("%06d", id);
    }
}

package com.fithub.model.base;

import com.fithub.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditListener.class)
@Data
public class BaseEntity {
    @Column(name = "create_date", updatable = false)
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @JoinColumn(name = "created_by", updatable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private User createdBy;

    @JoinColumn(name = "updated_by")
    @ManyToOne(cascade = CascadeType.ALL)
    private User updatedBy;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}

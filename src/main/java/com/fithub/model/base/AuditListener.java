package com.fithub.model.base;

import com.fithub.model.user.User;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class AuditListener {

    @PrePersist
    public void setAuditCreate(BaseEntity baseEntity) {
        baseEntity.setCreateDate(new Date(System.currentTimeMillis()));
        baseEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            baseEntity.setCreatedBy((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            baseEntity.setUpdatedBy((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
    }

    @PreUpdate
    public void setAuditUpdate(BaseEntity baseEntity) {
        baseEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        baseEntity.setUpdatedBy((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}

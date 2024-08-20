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
            try {

                baseEntity.setCreatedBy((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                baseEntity.setUpdatedBy((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                baseEntity.setCompany(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCompany());
            } catch (Exception e) {
                User admin = new User();
                admin.setId(1L);
                baseEntity.setCreatedBy(admin);
                baseEntity.setUpdatedBy(admin);
                baseEntity.setCompany(admin.getCompany());
                }
        }
    }

    @PreUpdate
    public void setAuditUpdate(BaseEntity baseEntity) {
        baseEntity.setUpdateDate(new Date(System.currentTimeMillis()));
        baseEntity.setUpdatedBy((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        baseEntity.setCompany(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCompany());
    }
}

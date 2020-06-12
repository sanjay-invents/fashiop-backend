package com.codezero.fashiop.shared.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable> extends AbstractPersistable<T> {

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Date createdDate = new Date();

    @LastModifiedDate
    @Column(name = "updated_date")
    private Date updatedDate = new Date();

    private boolean status;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        this.updatedDate = new Date();
    }

}

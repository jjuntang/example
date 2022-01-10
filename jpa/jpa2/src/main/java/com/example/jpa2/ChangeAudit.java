package com.example.jpa2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class ChangeAudit {
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDt;

    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime modifiedDt;
}

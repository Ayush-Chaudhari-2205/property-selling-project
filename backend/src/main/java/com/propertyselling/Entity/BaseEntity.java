package com.propertyselling.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name="created_on", updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name="updated_on")
    private LocalDateTime updatedOn;

    @Column(name="is_deleted",columnDefinition = "boolean default false")
    private boolean isActive = false;
}

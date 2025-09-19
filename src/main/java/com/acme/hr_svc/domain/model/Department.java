package com.acme.hr_svc.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name="departments",
        uniqueConstraints=@UniqueConstraint(name="uk_department_code", columnNames="code"))
public class Department {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=32)  private String code;
    @Column(nullable=false, length=128) private String name;
    @Column(nullable=false)             private Instant createdAt = Instant.now();
}
package com.acme.hr_svc.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name="employees",
        indexes=@Index(name="idx_emp_email", columnList="email"),
        uniqueConstraints=@UniqueConstraint(name="uk_emp_email", columnNames="email"))
public class Employee {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;

    @Column(length=64)  private String externalCode;
    @Column(nullable=false, length=64)  private String firstName;
    @Column(nullable=false, length=64)  private String lastName;
    @Column(nullable=false, length=160) private String email;
    @Column(nullable=false)             private LocalDate hireDate;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="department_id", foreignKey=@ForeignKey(name="fk_emp_dept"))
    private Department department;

    @Column(nullable=false, precision=15, scale=2) private BigDecimal salaryMonthly;

    @Enumerated(EnumType.STRING) @Column(nullable=false, length=16)
    private EmployeeStatus status = EmployeeStatus.ACTIVE;

    @Column(length=64) private String customerId; // vínculo opcional a billing

    @Column(nullable=false) private Instant createdAt = Instant.now();
    @Column(nullable=false) private Instant updatedAt = Instant.now();
    @PreUpdate public void onUpdate(){ this.updatedAt = Instant.now(); }
}

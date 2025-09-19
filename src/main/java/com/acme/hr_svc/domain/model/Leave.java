package com.acme.hr_svc.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name="leaves")
public class Leave {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="employee_id", foreignKey=@ForeignKey(name="fk_leave_emp"))
    private Employee employee;

    @Enumerated(EnumType.STRING) @Column(nullable=false, length=16) private LeaveType type;
    @Column(nullable=false) private LocalDate startDate;
    @Column(nullable=false) private LocalDate endDate;

    @Enumerated(EnumType.STRING) @Column(nullable=false, length=16)
    private LeaveStatus status = LeaveStatus.REQUESTED;

    @Column(nullable=false) private Instant createdAt = Instant.now();
}

package com.acme.hr_svc.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name="payroll_runs")
public class PayrollRun {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private int periodYear;
    @Column(nullable=false) private int periodMonth;
    @Column(nullable=false) private LocalDate runDate;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=16)
    private PayrollRunStatus status = PayrollRunStatus.OPEN;
}

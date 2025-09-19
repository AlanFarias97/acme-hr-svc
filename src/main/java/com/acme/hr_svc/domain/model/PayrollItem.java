package com.acme.hr_svc.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name="payroll_items")
public class PayrollItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="payroll_run_id", foreignKey=@ForeignKey(name="fk_item_run"))
    private PayrollRun payrollRun;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="employee_id", foreignKey=@ForeignKey(name="fk_item_emp"))
    private Employee employee;

    @Column(nullable=false, precision=15, scale=2) private BigDecimal gross;
    @Column(nullable=false, precision=15, scale=2) private BigDecimal deductions;
    @Column(nullable=false, precision=15, scale=2) private BigDecimal net;
}

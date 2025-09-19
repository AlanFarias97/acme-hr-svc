package com.acme.hr_svc.repository;
import com.acme.hr_svc.domain.model.PayrollRun;
import com.acme.hr_svc.domain.model.PayrollRunStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface PayrollRunRepository extends JpaRepository<PayrollRun, Long> {
    Optional<PayrollRun> findByPeriodYearAndPeriodMonth(int year, int month);
    boolean existsByPeriodYearAndPeriodMonthAndStatus(int y, int m, PayrollRunStatus status);
}
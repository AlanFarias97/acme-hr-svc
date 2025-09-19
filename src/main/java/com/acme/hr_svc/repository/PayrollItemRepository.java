package com.acme.hr_svc.repository;
import com.acme.hr_svc.domain.model.PayrollItem;
import com.acme.hr_svc.domain.model.PayrollRun;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PayrollItemRepository extends JpaRepository<PayrollItem, Long> {
    List<PayrollItem> findByPayrollRun(PayrollRun run);
}
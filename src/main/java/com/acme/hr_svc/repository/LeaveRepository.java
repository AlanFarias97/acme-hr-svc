package com.acme.hr_svc.repository;
import com.acme.hr_svc.domain.model.Leave;
import com.acme.hr_svc.domain.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployee_Id(Long employeeId);
    List<Leave> findByStatus(LeaveStatus status);
    List<Leave> findByEmployee_IdAndStatusAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
            Long employeeId, LeaveStatus status, LocalDate from, LocalDate to);
}

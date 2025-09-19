package com.acme.hr_svc.service;

import com.acme.hr_svc.domain.model.*;
import com.acme.hr_svc.repository.*;
import com.acme.hr_svc.service.dto.LeaveDto;
import com.acme.hr_svc.service.mapper.Mappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class LeaveService {
    private final LeaveRepository leaves; private final EmployeeRepository employees;

    public LeaveDto request(LeaveDto dto){
        if (dto.startDate().isAfter(dto.endDate())) throw new IllegalArgumentException("startDate must be <= endDate");
        Employee e = employees.findById(dto.employeeId()).orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        Leave l = Leave.builder().employee(e).type(LeaveType.valueOf(dto.type()))
                .startDate(dto.startDate()).endDate(dto.endDate()).status(LeaveStatus.REQUESTED).build();
        return Mappers.toDto(leaves.save(l));
    }

    public LeaveDto approve(Long id){
        Leave l = leaves.findById(id).orElseThrow(() -> new IllegalArgumentException("Leave not found"));
        l.setStatus(LeaveStatus.APPROVED);
        return Mappers.toDto(l);
    }

    public LeaveDto reject(Long id){
        Leave l = leaves.findById(id).orElseThrow(() -> new IllegalArgumentException("Leave not found"));
        l.setStatus(LeaveStatus.REJECTED);
        return Mappers.toDto(l);
    }

    @Transactional(readOnly = true)
    public List<LeaveDto> search(Long employeeId, String status, LocalDate from, LocalDate to){
        if (employeeId!=null && status!=null && from!=null && to!=null) {
            return leaves.findByEmployee_IdAndStatusAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
                    employeeId, LeaveStatus.valueOf(status), from, to).stream().map(Mappers::toDto).toList();
        }
        if (employeeId!=null) return leaves.findByEmployee_Id(employeeId).stream().map(Mappers::toDto).toList();
        if (status!=null)     return leaves.findByStatus(LeaveStatus.valueOf(status)).stream().map(Mappers::toDto).toList();
        return leaves.findAll().stream().map(Mappers::toDto).toList();
    }
}

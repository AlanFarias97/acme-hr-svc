package com.acme.hr_svc.service;

import com.acme.hr_svc.domain.model.*;
import com.acme.hr_svc.repository.*;
import com.acme.hr_svc.service.dto.EmployeeDto;
import com.acme.hr_svc.service.mapper.Mappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class EmployeeService {
    private final EmployeeRepository employees; private final DepartmentRepository departments;

    public EmployeeDto create(EmployeeDto dto){
        if (employees.existsByEmail(dto.email())) throw new IllegalArgumentException("Email already exists");
        Department dept = departments.findById(dto.departmentId()).orElseThrow(() -> new IllegalArgumentException("Department not found"));
        Employee e = Employee.builder()
                .externalCode(dto.externalCode()).firstName(dto.firstName()).lastName(dto.lastName())
                .email(dto.email()).hireDate(dto.hireDate()).department(dept)
                .salaryMonthly(dto.salaryMonthly()).customerId(dto.customerId())
                .status(EmployeeStatus.ACTIVE).build();
        return Mappers.toDto(employees.save(e));
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> list(String deptCode){
        return employees.findAll().stream().filter(e -> deptCode==null ||
                        (e.getDepartment()!=null && deptCode.equalsIgnoreCase(e.getDepartment().getCode())))
                .map(Mappers::toDto).toList();
    }

    @Transactional(readOnly = true)
    public EmployeeDto get(Long id){
        return employees.findById(id).map(Mappers::toDto).orElseThrow(() -> new IllegalArgumentException("Employee not found"));
    }
}

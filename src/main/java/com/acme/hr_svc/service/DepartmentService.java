package com.acme.hr_svc.service;

import com.acme.hr_svc.domain.model.Department;
import com.acme.hr_svc.repository.DepartmentRepository;
import com.acme.hr_svc.service.dto.DepartmentDto;
import com.acme.hr_svc.service.mapper.Mappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class DepartmentService {
    private final DepartmentRepository repo;

    public DepartmentDto create(DepartmentDto dto){
        repo.findByCode(dto.code()).ifPresent(d -> { throw new IllegalArgumentException("Department code already exists"); });
        Department d = Department.builder().code(dto.code()).name(dto.name()).build();
        return Mappers.toDto(repo.save(d));
    }

    @Transactional(readOnly = true)
    public List<DepartmentDto> list(){ return repo.findAll().stream().map(Mappers::toDto).toList(); }
}

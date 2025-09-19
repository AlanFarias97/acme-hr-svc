package com.acme.hr_svc.web.controller;

import com.acme.hr_svc.service.DepartmentService;
import com.acme.hr_svc.service.dto.DepartmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController @RequestMapping("/departments") @RequiredArgsConstructor
@PreAuthorize("hasAnyRole('HR_ADMIN','HR_USER')")
public class DepartmentController {
    private final DepartmentService svc;

    @PostMapping @PreAuthorize("hasRole('HR_ADMIN')")
    public DepartmentDto create(@Valid @RequestBody DepartmentDto dto){ return svc.create(dto); }

    @GetMapping
    public List<DepartmentDto> list(){ return svc.list(); }
}

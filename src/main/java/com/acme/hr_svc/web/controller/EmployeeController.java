package com.acme.hr_svc.web.controller;

import com.acme.hr_svc.service.EmployeeService;
import com.acme.hr_svc.service.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController @RequestMapping("/employees") @RequiredArgsConstructor
@PreAuthorize("hasAnyRole('HR_ADMIN','HR_USER')")
public class EmployeeController {
    private final EmployeeService svc;

    @PostMapping @PreAuthorize("hasRole('HR_ADMIN')")
    public EmployeeDto create(@Valid @RequestBody EmployeeDto dto){ return svc.create(dto); }

    @GetMapping
    public List<EmployeeDto> list(@RequestParam(required=false) String dept){ return svc.list(dept); }

    @GetMapping("/{id}")
    public EmployeeDto get(@PathVariable Long id){ return svc.get(id); }
}

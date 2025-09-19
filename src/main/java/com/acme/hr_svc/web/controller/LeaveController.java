package com.acme.hr_svc.web.controller;

import com.acme.hr_svc.service.LeaveService;
import com.acme.hr_svc.service.dto.LeaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate; import java.util.List;

@RestController @RequestMapping("/leaves") @RequiredArgsConstructor
@PreAuthorize("hasAnyRole('HR_ADMIN','HR_USER')")
public class LeaveController {
    private final LeaveService svc;

    @PostMapping
    public LeaveDto create(@Valid @RequestBody LeaveDto dto){ return svc.request(dto); }

    @PutMapping("/{id}/approve") @PreAuthorize("hasRole('HR_ADMIN')")
    public LeaveDto approve(@PathVariable Long id){ return svc.approve(id); }

    @PutMapping("/{id}/reject")  @PreAuthorize("hasRole('HR_ADMIN')")
    public LeaveDto reject(@PathVariable Long id){ return svc.reject(id); }

    @GetMapping
    public List<LeaveDto> search(@RequestParam(required=false) Long employeeId,
                                 @RequestParam(required=false) String status,
                                 @RequestParam(required=false) LocalDate from,
                                 @RequestParam(required=false) LocalDate to){
        return svc.search(employeeId, status, from, to);
    }
}

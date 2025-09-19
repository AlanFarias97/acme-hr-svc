package com.acme.hr_svc.web.controller;

import com.acme.hr_svc.service.PayrollService;
import com.acme.hr_svc.service.dto.PayrollItemDto;
import com.acme.hr_svc.service.dto.PayrollRunDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/payroll") @RequiredArgsConstructor
@PreAuthorize("hasAnyRole('HR_ADMIN','HR_USER')")
public class PayrollController {
    private final PayrollService svc;

    @PostMapping("/runs")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public PayrollRunDto open(@RequestParam int year, @RequestParam int month){
        return svc.open(year, month);
    }

    @PostMapping("/runs/{id}/close")
    @PreAuthorize("hasRole('HR_ADMIN')")
    public PayrollRunDto close(@PathVariable Long id){ return svc.close(id); }

    @GetMapping("/runs/{id}/items")
    public List<PayrollItemDto> items(@PathVariable Long id){ return svc.items(id); }
}

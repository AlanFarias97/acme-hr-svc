package com.acme.hr_svc.service;

import com.acme.hr_svc.domain.model.*;
import com.acme.hr_svc.repository.*;
import com.acme.hr_svc.service.dto.PayrollItemDto;
import com.acme.hr_svc.service.dto.PayrollRunDto;
import com.acme.hr_svc.service.mapper.Mappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class PayrollService {
    private final PayrollRunRepository runs;
    private final PayrollItemRepository items;
    private final EmployeeRepository employees;

    public PayrollRunDto open(int year, int month){
        if (runs.existsByPeriodYearAndPeriodMonthAndStatus(year, month, PayrollRunStatus.OPEN))
            throw new IllegalArgumentException("Run already open for period");
        PayrollRun r = PayrollRun.builder().periodYear(year).periodMonth(month).runDate(LocalDate.now())
                .status(PayrollRunStatus.OPEN).build();
        return Mappers.toDto(runs.save(r));
    }

    public PayrollRunDto close(Long runId){
        PayrollRun r = runs.findById(runId).orElseThrow(() -> new IllegalArgumentException("Run not found"));
        if (r.getStatus()==PayrollRunStatus.CLOSED) return Mappers.toDto(r);

        // generar items simples (gross = salario, deductions = 0, net = gross)
        List<Employee> emps = employees.findAll().stream()
                .filter(e -> e.getStatus()==EmployeeStatus.ACTIVE).toList();
        for (Employee e : emps) {
            var gross = e.getSalaryMonthly();
            var deductions = BigDecimal.ZERO;
            var net = gross.subtract(deductions);
            items.save(PayrollItem.builder()
                    .payrollRun(r).employee(e).gross(gross).deductions(deductions).net(net).build());
        }
        r.setStatus(PayrollRunStatus.CLOSED);

        // TODO: aquí podrías llamar a billing-svc para generar invoice(s)
        return Mappers.toDto(r);
    }

    @Transactional(readOnly = true)
    public List<PayrollItemDto> items(Long runId){
        PayrollRun r = runs.findById(runId).orElseThrow(() -> new IllegalArgumentException("Run not found"));
        return items.findByPayrollRun(r).stream().map(Mappers::toDto).toList();
    }
}

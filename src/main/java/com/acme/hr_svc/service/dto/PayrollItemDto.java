package com.acme.hr_svc.service.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal;
public record PayrollItemDto(Long id, @NotNull Long payrollRunId, @NotNull Long employeeId,
                             @NotNull @DecimalMin("0.0") BigDecimal gross,
                             @NotNull @DecimalMin("0.0") BigDecimal deductions, BigDecimal net) {}

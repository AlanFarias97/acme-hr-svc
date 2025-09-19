package com.acme.hr_svc.service.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.time.LocalDate;
public record EmployeeDto(
        Long id, @Size(max=64) String externalCode,
        @NotBlank @Size(max=64) String firstName,
        @NotBlank @Size(max=64) String lastName,
        @NotBlank @Email @Size(max=160) String email,
        @NotNull LocalDate hireDate, @NotNull Long departmentId,
        @NotNull @DecimalMin(value="0.0", inclusive=false) BigDecimal salaryMonthly,
        @Size(max=64) String customerId, String status) {}

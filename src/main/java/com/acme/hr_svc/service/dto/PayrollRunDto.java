package com.acme.hr_svc.service.dto;
import jakarta.validation.constraints.*; import java.time.LocalDate;
public record PayrollRunDto(Long id, @NotNull Integer periodYear,
                            @NotNull @Min(1) @Max(12) Integer periodMonth,
                            LocalDate runDate, String status) {}

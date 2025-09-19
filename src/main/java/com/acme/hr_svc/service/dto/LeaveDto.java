package com.acme.hr_svc.service.dto;
import jakarta.validation.constraints.NotNull; import java.time.LocalDate;
public record LeaveDto(Long id, @NotNull Long employeeId, @NotNull String type,
                       @NotNull LocalDate startDate, @NotNull LocalDate endDate,
                       String status) {}
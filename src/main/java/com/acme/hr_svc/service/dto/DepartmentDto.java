package com.acme.hr_svc.service.dto;
import jakarta.validation.constraints.NotBlank; import jakarta.validation.constraints.Size;
public record DepartmentDto(Long id, @NotBlank @Size(max=32) String code,
                            @NotBlank @Size(max=128) String name) {}

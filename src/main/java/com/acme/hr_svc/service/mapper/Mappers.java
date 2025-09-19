package com.acme.hr_svc.service.mapper;

import com.acme.hr_svc.domain.model.*;
import com.acme.hr_svc.service.dto.*;

public class Mappers {
    public static DepartmentDto toDto(Department d){ return new DepartmentDto(d.getId(), d.getCode(), d.getName()); }
    public static EmployeeDto toDto(Employee e){ return new EmployeeDto(
            e.getId(), e.getExternalCode(), e.getFirstName(), e.getLastName(), e.getEmail(),
            e.getHireDate(), e.getDepartment().getId(), e.getSalaryMonthly(),
            e.getCustomerId(), e.getStatus()!=null?e.getStatus().name():null); }
    public static LeaveDto toDto(Leave l){ return new LeaveDto(l.getId(), l.getEmployee().getId(),
            l.getType().name(), l.getStartDate(), l.getEndDate(), l.getStatus().name()); }
    public static PayrollRunDto toDto(PayrollRun r){ return new PayrollRunDto(r.getId(),
            r.getPeriodYear(), r.getPeriodMonth(), r.getRunDate(), r.getStatus().name()); }
    public static PayrollItemDto toDto(PayrollItem i){ return new PayrollItemDto(i.getId(),
            i.getPayrollRun().getId(), i.getEmployee().getId(), i.getGross(), i.getDeductions(), i.getNet()); }
}

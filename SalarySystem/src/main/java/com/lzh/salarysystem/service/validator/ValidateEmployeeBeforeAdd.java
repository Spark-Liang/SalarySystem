package com.lzh.salarysystem.service.validator;

import org.springframework.stereotype.Component;

import com.lzh.salarysystem.entity.Employee;
import com.lzh.salarysystem.entity.HourlyEmployee;
import com.lzh.salarysystem.entity.MonthlyEmployee;
import com.lzh.salarysystem.entity.SaleEmployee;
import com.lzh.salarysystem.exception.AddressIsNull;
import com.lzh.salarysystem.exception.AddressIsTooLong;
import com.lzh.salarysystem.exception.CommissionRateNotAPositiveNumber;
import com.lzh.salarysystem.exception.HourlyRateNotAPositiveNumber;
import com.lzh.salarysystem.exception.MonthlySalaryNotAPositiveNumber;
import com.lzh.salarysystem.exception.NameIsNull;
import com.lzh.salarysystem.exception.NameIsTooLong;
import com.lzh.salarysystem.service.Validator;

@Component("ValidateEmployeeForAdd")
public class ValidateEmployeeBeforeAdd implements Validator<Employee>{

	@Override
	public void validate(Employee target) {
		if(target instanceof MonthlyEmployee) {
			validateMonthlyEmployee(target);
		}
		if(target instanceof HourlyEmployee) {
			validateHourlyEmployee(target);
		}
		validateName(target);
		validateAddress(target);
	}

	private void validateMonthlyEmployee(Employee target) {
		if(target instanceof SaleEmployee) {
			validateSaleEmployee(target);
		}
		MonthlyEmployee monthlyEmployee = (MonthlyEmployee)target;
		if(!(monthlyEmployee.getMonthlySalary().compareTo(MonthlyEmployee.MIN_MONTH_SALARY) > 0)){
			throw new MonthlySalaryNotAPositiveNumber();
		}
	}

	private void validateSaleEmployee(Employee target) {
		SaleEmployee saleEmployee = (SaleEmployee)target;
		if(!(saleEmployee.getCommissionRate().compareTo(SaleEmployee.MIN_COMMISSION_RATE) > 0)){
			throw new CommissionRateNotAPositiveNumber();
		}
	}

	private void validateHourlyEmployee(Employee target) {
		HourlyEmployee hourlyEmployee = (HourlyEmployee)target;
		if(!(hourlyEmployee.getHourlyRate().compareTo(HourlyEmployee.MIN_HOURLY_RATE) > 0)) {
			throw new HourlyRateNotAPositiveNumber();
		}
	}
	
	private void validateAddress(Employee employee) {
		if (null == employee.getAddress()) {
			throw new AddressIsNull();
		}
		if(employee.getAddress().length() > Employee.MAX_ADDRESS_LENGTH) {
			throw new AddressIsTooLong();
		}
	}

	private void validateName(Employee employee) {
		if (null == employee.getName()) {
			throw new NameIsNull();
		}
		if(employee.getName().length() > Employee.MAX_NAME_LENGTH) {
			throw new NameIsTooLong();
		}
	}
}

create table Employee
(
	empID int primary key identity
	,name varchar(32) not null
	,address varchar(1024) not null
	,employeeType varchar(16) not null
	,hourlyRate decimal(10,4) not null default 0
	,monthlySalary decimal(20,4) not null default 0
	,commissionRate decimal(10,4) not null default 0
);
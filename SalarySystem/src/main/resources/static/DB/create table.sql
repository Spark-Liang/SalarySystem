create table Employee
(
	id int primary key identity
	,name varchar(32) not null
	,address varchar(1024) not null
	,employeeType varchar(16) not null
	,hourlyRate decimal(10,4) not null default 0
	,monthlySalary decimal(20,4) not null default 0
	,commissionRate decimal(10,4) not null default 0
);


create table TimeCard
(
	empID int not null
	,workDate date not null
	,hours int not null
	,primary key (embID,workDate)
);

create table WorkRecord
(
	id bigint primary key identity
	,empID int not null
	,startTime time not null
	,endTime time
);

create table WorkRecordHist
(
	id bigint primary key identity
	,empID int not null
	,startTime time not null
	,endTime time not null
	,workDate date not null
);


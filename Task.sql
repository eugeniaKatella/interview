CREATE TABLE `employee_salaries` (
         `employeeId` int,
         `departmentId` int,
         `profession` int,        -- positions 1,2,3
         `salary` int
) ENGINE=InnoDB;


-- Get department ids where there are employees with profession 1
-- and all of that employees have salaries more then 10 000
-- (without joins and multiple selects)
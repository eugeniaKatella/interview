CREATE TABLE `employees` (
                           `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                           `name` varchar(50) NOT NULL,
                           `gender` tinyint(1) NOT NULL,
                           `age` tinyint(1) NOT NULL,
                           PRIMARY KEY (`id`),
                           KEY `age_gender` (`age`,`gender`)
) ENGINE=InnoDB;

explain select * from employees where age = 25;
explain select * from employees where age < 25;
explain select * from employees where age < 25 and gender = 1;





CREATE TABLE `shifts` (
                      `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                      `departmentId` int(11) NOT NULL,
                      `employeeId` int(11) NOT NULL,
                      `shiftStart` datetime NOT NULL,
                      `shiftEnd` datetime NOT NULL,
                      PRIMARY KEY (`id`),
                      KEY `composIndex` (`departmentId`,`shiftStart`,`shiftEnd`)
) ENGINE=InnoDB;


select * from shifts where departmentId = 123 and shiftStart > '2020-01-01 00:00:00';
select * from shifts where departmentId = 123 and shiftStart > '2020-01-01 00:00:00' and shiftEnd < '2021-01-01 00:00:00';
select * from shifts where shiftStart = '2020-01-01 08:00:00';


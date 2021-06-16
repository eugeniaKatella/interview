<?php
declare(strict_types = 1);

class SalariesCalculator
{
    private array $employeeIds;

    /**
     * @return array
     */
    public function getEmployeeIds(): array
    {
        return $this->employeeIds;
    }

    /**
     * @param array $employeeIds
     * @return SalariesCalculator
     */
    public function setEmployeeIds(array $employeeIds): self
    {
        $this->employeeIds = $employeeIds;

        return $this;
    }

    /**
     * @return array
     */
    public function calculatePeriodSalaries(): array
    {
        $database = new Database;
        $employeeRepository = new EmployeeRepository($database);
        $employmentTypeRepository = new EmploymentTypeRepository($database);

        foreach ($this->getEmployeeIds() as $employeeId) {

            $employee = $employeeRepository->getEmployee($employeeId);

            $employmentType = $employmentTypeRepository->getEmploymentType($employee['employmentTypeId']);

            $periodSalary = $employee['dailySalary'] * $employmentType['daysInPeriod'] * $employmentType['employmentRate'];

            $periodSalaries[$employeeId] = $periodSalary;
        }

        return $periodSalaries;
    }

    /**
     * @param array $employees
     * @param array $departments
     * @return array
     */
    public function getSalaryReports(array $employees, array $departments): array
    {
        foreach ($employees as &$item) {
            $item['hourlySalary'] = $item['dailySalary'] / $departments[$item['departmentId']]['workingHours'];
        }

        $salaryTypes = [];
        foreach ($departments as $item) {
            $salaryTypes[] = $item['salaryType'];
        }

        return $this->generateReports($employees, $salaryTypes);

    }

    /**
     * @param $employees
     * @param $salaryTypes
     * @return array
     */
    protected function generateReports ($employees, $salaryTypes): array
    {
        $report = [];

        //.... does not matter, some logic

        return $report;
    }
}

final class EmployeeRepository
{
    protected Database $database;

    /**
     * EmployeeRepository constructor.
     * @param DatabaseInterface $database
     */
    public function __construct(DatabaseInterface $database)
    {
        $this->database = $database;
    }

    /**
     * @param int $id
     * @return array
     */
    public function getEmployee(int $id): array
    {
        $query = $this->database->select('employee', '*')->where(['id' => $id]);

        return $query->get();
    }

}

final class EmploymentTypeRepository
{
    protected Database $database;

    /**
     * EmploymentTypeRepository constructor.
     * @param DatabaseInterface $database
     */
    public function __construct(DatabaseInterface $database)
    {
        $this->database = $database;
    }

    /**
     * @param int $id
     * @return array
     */
    public function getEmploymentType(int $id): array
    {
        $query = $this->database->select('employmenttype', '*')->where(['id' => $id]);

        return $query->get();
    }

}

class Database implements DatabaseInterface {}
interface DatabaseInterface {}

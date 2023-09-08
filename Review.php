<?php
declare(strict_types = 1);

class SalariesCalculator
{
    private $employeeIds;

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
     * @param array $data
     * @return void
     */
    public function setDepartmentsData(array $data): void
    {
        $this->departmentsData = $data;
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

            $departmentCorrectionFactor = $this->getDepartmentCorrectionFactorByDepartmentId($employee['departmentId']);

            $periodSalary = $employee['dailySalary']
                * $employmentType['daysInPeriod']
                * $employmentType['employmentRate']
                * $departmentCorrectionFactor;

            $periodSalaries[$employeeId] = $periodSalary;
        }

        return $periodSalaries;
    }

    /**
     * @param int $departmentId
     * @return int
     */
    private function getDepartmentCorrectionFactorByDepartmentId(int $departmentId): int
    {
        return $this->departmentsData[$departmentId]['factor'] ?? 1;
    }

    /**
     * @param array $employees
     * @param array $departments
     * @return array
     * @throws Exception
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

        return $this->failIfReportIsCorrupted($this->generateReports($employees, $salaryTypes));

    }

    /**
     * @param array $report
     * @return never
     * @throws Exception
     */
    private function failIfReportIsCorrupted(array $report): never
    {
        if (isset($report['status']) && $report['status'] === 'OK') {
            return $report;
        }
        throw new Exception('Error generation report. ID - ' . $report['id'] ?? '-');
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
    private Database $database;

    /**
     * EmploymentTypeRepository constructor.
     * @param DatabaseInterface $database
     */
    public function __construct(private DatabaseInterface $database)
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

final readonly class DepartmentService
{
    private $cache = null;

    public function __construct(
        private DatabaseInterface $database,
        protected ExternalApIService $externalAPIService,
        CacheInterface $cache
    )
    {
        $this->cache = $cache;
    }
}

class Database implements DatabaseInterface {}
interface DatabaseInterface {}
interface ExternalAPIService {}
interface CacheInterface {}
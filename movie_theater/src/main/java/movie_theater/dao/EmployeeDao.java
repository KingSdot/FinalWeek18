package movie_theater.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import movie_theater.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}

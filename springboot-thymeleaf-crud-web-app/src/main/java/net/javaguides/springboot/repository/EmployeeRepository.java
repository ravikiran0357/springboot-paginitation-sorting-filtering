package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	@Query("SELECT p FROM Employee p WHERE CONCAT(p.firstName, ' ', p.lastName, ' ', p.email) LIKE %?1%")
	List<Employee> search(String keyword);
	@Query("SELECT p FROM Employee p WHERE CONCAT(p.firstName, ' ', p.lastName, ' ', p.email) LIKE %?1%")
	Page<Employee>  searching(String keyword, Pageable pageable);
	Page<Employee> findByfirstName(String firstName, Pageable pageable);

}

package net.javaguides.springboot.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.ResponseBean;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeRepository employeeRepo;
	//changed
	// display list of employees
	@GetMapping("/")
	public ResponseEntity<?> viewHomePage( @RequestParam(required = false) String title,@RequestParam(defaultValue = "1",required = false) int pageNo,
	        @RequestParam(defaultValue = "5") int size,@RequestParam(defaultValue = "asc",required = false) String sortDir) {
		  String sortField="firstName";
		  
		  Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
				Sort.by(sortField).descending();
			
			Pageable pageable = PageRequest.of(pageNo - 1, size, sort);
		      List<Employee> tutorials = new ArrayList<Employee>();
		      
		      
		      Page<Employee> pageTuts;
		      if (title == null)
		        pageTuts = employeeRepo.findAll(pageable);
		      else
		        pageTuts = employeeRepo.searching(title, pageable);
		      tutorials = pageTuts.getContent();
		      Map<String, Object> response = new HashMap<>();
		      response.put("totals List", tutorials);
		      response.put("currentPage", pageTuts.getNumber());
		      response.put("totalItems", pageTuts.getTotalElements());
		      response.put("totalPages", pageTuts.getTotalPages());
		      return new ResponseEntity<>(response, HttpStatus.OK);
		//return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		// save employee to database
		employeeService.saveEmployee(employee);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		Employee employee = employeeService.getEmployeeById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}
	
/*	
	@GetMapping("/page/{pageNo}")
	public ResponseEntity<?> findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		ResponseBean responseBean = new ResponseBean();
		int pageSize = 5;
		
		Long startTime = Calendar.getInstance().getTimeInMillis();
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		responseBean.setObject(listEmployees);
		Long endTime = Calendar.getInstance().getTimeInMillis();
		responseBean.setResponseTime(endTime - startTime);
		responseBean.setStatusCD(200);
		responseBean.setStatusMSG("Content listAll Sucessfully");
		*/
//		
//		model.addAttribute("currentPage", pageNo);
//		model.addAttribute("totalPages", page.getTotalPages());
//		model.addAttribute("totalItems", page.getTotalElements());
//		
//		model.addAttribute("sortField", sortField);
//		model.addAttribute("sortDir", sortDir);
//		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
//		
//		model.addAttribute("listEmployees", listEmployees);
	/*	return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}*/
	@GetMapping("/search")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Employee> listEmployees = employeeService.listAll(keyword);
        model.addAttribute("listEmployees", listEmployees);
        return "index";
    }
	/*
	 * @GetMapping("/pages/{pageNo}") public ResponseEntity<?>
	 * nextPage(@PathVariable (value="pageNo" int pageNo,@RequestParam("sorDir")))
	 */
}

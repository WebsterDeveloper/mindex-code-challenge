package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure getReportingStructure(String id) {
    	LOG.debug("Getting reporting structure for id [{}]", id);
    	
        Employee employee = employeeRepository.findByEmployeeId(id);
        
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        ReportingStructure rs = new ReportingStructure();
        rs.setEmployee(employee);
        rs.setNumberOfReports(calculateNumberOfReports(employee));
        return rs;
    }
    
    // Recursive method for determining the number of reports for an employee
    private int calculateNumberOfReports(Employee employee) {
    	int reports = 0;
    	if ((employee.getDirectReports() != null) && (employee.getDirectReports().size() > 0)) {
    		for (Employee report: employee.getDirectReports()) {
        		Employee subordinate = employeeRepository.findByEmployeeId(report.getEmployeeId());
    			reports++;
    			reports += calculateNumberOfReports(subordinate);
    		}
    	}
    	
    	return reports;
    }
}

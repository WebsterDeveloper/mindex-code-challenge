package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    /*
     * Use as follows:
     * 
     * HTTP Method: POST
     * URL: http://localhost:8080/compensation/
     * Content-type: application/json
     * Payload Body:
     * {
     * 	"employeeId" : "16a596ae-edd3-4847-99fe-c4518e82c86f",
     *  "salary" : "99999.99",
     * 	"effectiveDate" : "2020-02-18"
     * }
     */
    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation.getEmployeeId());
        
        return compensationService.create(compensation);
    }

	/*    
     * Use as follows:
     * 
     * HTTP Method: GET
     * URL: http://localhost:8080/compensation/{id}
     * 
     */
    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation read request for id [{}]", id);
        
        return compensationService.read(id);
    }
}
package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Compensation;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationCreateUrl;
    private String compensationReadUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
    	compensationCreateUrl = "http://localhost:" + port + "/compensation";
    	compensationReadUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateRead() throws ParseException {
		Compensation testCompensation = new Compensation();
		testCompensation.setEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f"); 
		testCompensation.setSalary(12345.99);
		testCompensation.setEffectiveDate("2020-02-16");

        // Create check
        Compensation createdCompensation = restTemplate.postForEntity(compensationCreateUrl, testCompensation, Compensation.class).getBody();
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Read check
        Compensation readCompensation = restTemplate.getForEntity(compensationReadUrl, Compensation.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
        assertCompensationEquivalence(testCompensation, readCompensation);
    }
    
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getSalary(), actual.getSalary(), 0.0);
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
    }
}

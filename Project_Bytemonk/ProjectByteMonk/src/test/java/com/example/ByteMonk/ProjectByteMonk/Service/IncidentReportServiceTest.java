package com.example.ByteMonk.ProjectByteMonk.Service;

import com.example.ByteMonk.ProjectByteMonk.Model.IncidentReport;
import com.example.ByteMonk.ProjectByteMonk.Repository.IncidentRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class IncidentReportServiceTest {


    @Mock
    private IncidentRepo repo;

    @InjectMocks
    private IncidentReportService service;

    private AutoCloseable autoCloseable;
    private IncidentReport report;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        report = new IncidentReport();
        report.setId(1);
        report.setTitle("Network Issue");
        report.setDescription("Network outage in building A");
        report.setIncidentDate(LocalDate.now());
        report.setSeverity("High");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();

    }

    @Test
    void getAllIncidents() {
        when(repo.findAll()).thenReturn(List.of(report));


        ResponseEntity<?> response = service.getAllIncidents();

        // Assertions
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        assertEquals(1, ((List<?>) response.getBody()).size(), "Should return one incident");
        assertEquals(report, ((List<?>) response.getBody()).get(0), "Returned incident should match the mock");
    }

    @Test
    void getIncidentReportById() {
        when(repo.findById(1)).thenReturn(Optional.of(report));

        ResponseEntity<?> response = service.getIncidentReportById(1);

        // Assertions
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK");
        assertEquals(report, response.getBody(), "Returned incident should match the mock");
    }

    @Test
    void getIncidentReportById_NotFound() {
        // Arrange
        when(repo.findById(999)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = service.getIncidentReportById(999);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Status code should be NOT_FOUND");
        assertEquals("Incident not found.", response.getBody(), "Response body should indicate incident not found."); // Adjusted expected message
    }

}
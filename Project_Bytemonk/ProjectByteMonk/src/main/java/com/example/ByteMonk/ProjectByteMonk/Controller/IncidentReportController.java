package com.example.ByteMonk.ProjectByteMonk.Controller;

import com.example.ByteMonk.ProjectByteMonk.Model.IncidentReport;
import com.example.ByteMonk.ProjectByteMonk.Service.IncidentReportService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/incident")
public class IncidentReportController {

    @Autowired
    private IncidentReportService service;


    @GetMapping("/getCsrfToken")
    public CsrfToken getScrfToken(HttpServletRequest req){
        return (CsrfToken) req.getAttribute("_csrf");
    }
    @PostMapping("/addIncident")
    public ResponseEntity<?> createIncident(@RequestBody IncidentReport report){
        return service.createIncidentReport(report);
    }

    @GetMapping("/getAllIncidents")
    public ResponseEntity<?> getAllIncidents(){
        return service.getAllIncidents();
    }


    @GetMapping("getIncident/{id}")
    public ResponseEntity<?> getIncidentById(@PathVariable int id){
        return service.getIncidentReportById(id);
    }

    @PutMapping("updateIncident/{id}")
    public ResponseEntity<?> updateIncident(@PathVariable int id,@RequestBody IncidentReport report){
        return service.updateIncidentReport(id, report);
    }


    @GetMapping("/incidents")
    public ResponseEntity<?> getParticulars(@RequestParam String severity, @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy")LocalDate startDate, @RequestParam @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate endDate){
        return service.getParticularIncident(severity, startDate, endDate);
    }



}

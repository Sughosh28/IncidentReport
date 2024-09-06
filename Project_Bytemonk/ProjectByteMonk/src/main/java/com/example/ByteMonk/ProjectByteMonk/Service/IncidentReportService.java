package com.example.ByteMonk.ProjectByteMonk.Service;


import com.example.ByteMonk.ProjectByteMonk.Model.IncidentReport;
import com.example.ByteMonk.ProjectByteMonk.Repository.IncidentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class IncidentReportService {

    @Autowired
    private IncidentRepo repo;


    //create new incident
    public ResponseEntity<?> createIncidentReport(IncidentReport report) {

        LocalDate incidentDate=report.getIncidentDate();

        LocalDate todaysDate= LocalDate.now();

        if(incidentDate.isBefore(todaysDate.minusDays(30)) || incidentDate.isAfter(todaysDate)) {
            return new ResponseEntity<>( "Incident date must be within the last 30 days and not in the future", HttpStatus.BAD_REQUEST);
        }


        if( !severityValidation(report.getSeverity())){
            return new ResponseEntity<>( "Invalid.", HttpStatus.BAD_REQUEST);

        }

        if(report.getTitle().length()<10  ){
            return new ResponseEntity<>( "The title must have at least 10 characters.", HttpStatus.BAD_REQUEST);

        }

        if(repo.findByTitle(report.getTitle()).isPresent()){
            return new ResponseEntity<>( "The incident with the same title already exists.", HttpStatus.BAD_REQUEST);

        }

        IncidentReport saveNewReport= repo.save(report);
        return new ResponseEntity<>( saveNewReport, HttpStatus.OK);
    }

    private boolean severityValidation(String severity) {
        List<String> severityList= Arrays.asList("Low", "Medium", "High", "Critical");
        return severityList.contains(severity);
    }


    //get all incidents
    public ResponseEntity<?> getAllIncidents() {
    try{
        List<IncidentReport> reports= repo.findAll();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
    catch(Exception e){
        return new ResponseEntity<>("An error occurred while retrieving incidents.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }


    //getReportsById
    public ResponseEntity<?> getIncidentReportById(int id) {
        try {
            Optional<IncidentReport> report = repo.findById(id);
            if (report.isPresent()) {
                return new ResponseEntity<>(report.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Incident not found.", HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<>("An error occurred while retrieving THE incident.", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> updateIncidentReport( int id, IncidentReport report) {
        LocalDate incidentDate=report.getIncidentDate();
        LocalDate todaysDate = LocalDate.now();

        if (incidentDate.isBefore(todaysDate.minusDays(30)) || incidentDate.isAfter(todaysDate)) {
            return new ResponseEntity<>("Incident date must be within the last 30 days and not in the future", HttpStatus.BAD_REQUEST);
        }

        if (!severityValidation(report.getSeverity())) {
            return new ResponseEntity<>("Invalid severity.", HttpStatus.BAD_REQUEST);
        }

        if (report.getTitle().length() < 10) {
            return new ResponseEntity<>("The title must have at least 10 characters.", HttpStatus.BAD_REQUEST);
        }

        Optional<IncidentReport> existingReport = repo.findByTitle(report.getTitle());
        if (existingReport.isPresent() && existingReport.get().getId() != id) {

            return new ResponseEntity<>("The incident with the same title already exists.", HttpStatus.BAD_REQUEST);
        }

        try {
            if (!repo.existsById(id)) {
                return new ResponseEntity<>("Incident not found.", HttpStatus.NOT_FOUND);
            }

            report.setId(id);

            IncidentReport updatedReport = repo.save(report);
            return new ResponseEntity<>(updatedReport, HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Data integrity violation occurred.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the incident.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //get specific results
    public ResponseEntity<?> getParticularIncident(String severity, LocalDate startDate, LocalDate endDate) {
        try {
            List<IncidentReport> reports = repo.findByFilters(severity, startDate, endDate);
        return new ResponseEntity<>(reports, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("An error occurred while retrieving incidents.", HttpStatus.BAD_REQUEST);
        }

    }
}

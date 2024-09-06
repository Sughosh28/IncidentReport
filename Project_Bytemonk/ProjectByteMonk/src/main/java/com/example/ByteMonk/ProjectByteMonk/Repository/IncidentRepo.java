package com.example.ByteMonk.ProjectByteMonk.Repository;


import com.example.ByteMonk.ProjectByteMonk.Model.IncidentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncidentRepo extends JpaRepository<IncidentReport, Integer> {


    @Query("SELECT r FROM  IncidentReport r " +
            "WHERE (?1 IS NULL OR r.severity= ?1 ) "+
            "AND (?2 is NULL OR r.incidentDate>=?2) "+
            "AND (?3 is NULL OR r.incidentDate<= ?3)")
    List<IncidentReport> findByFilters(String severity, LocalDate startDate, LocalDate endDate);

    Optional<IncidentReport> findByTitle(String title);


}

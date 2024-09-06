package com.example.ByteMonk.ProjectByteMonk.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IncidentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotNull(message = "Title cannot be empty.")
    @Size(min=10, message = "Title must have at least 10 characters")
    private String title;

    private String description;

    @DateTimeFormat(pattern="dd-MM-yyyy")
    private LocalDate incidentDate;

    @NotNull(message = "Severity cannot be null.")
    private String severity;
}

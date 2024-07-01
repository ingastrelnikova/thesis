package example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class PatientDto {
    private Long anonymizedId;
    private String name;
    private String dateOfBirth;
    private String zipCode;
    private String gender;
    private String disease;

    // Constructors, getters, and setters

    public PatientDto() {}

    public PatientDto(Long anonymizedId, String name, String dateOfBirth, String zipCode, String gender, String disease) {
        this.anonymizedId = anonymizedId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.zipCode = zipCode;
        this.gender = gender;
        this.disease = disease;
    }

    public Long getId() {
        return anonymizedId;
    }

    public void setId(Long id) {
        this.anonymizedId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
// Getters and setters
}

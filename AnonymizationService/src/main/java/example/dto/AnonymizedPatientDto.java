package example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class AnonymizedPatientDto {
    private Long anonymizedId;
    private String anonymizedName;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String anonymizedDateOfBirth;
    private String zipCode;
    private String gender;
    private String disease;

    public Long getAnonymizedId() {
        return anonymizedId;
    }

    public void setAnonymizedId(Long anonymizedId) {
        this.anonymizedId = anonymizedId;
    }
// Constructors, getters, and setters

    public AnonymizedPatientDto() {}

    public AnonymizedPatientDto(Long anonymizedId, String anonymizedName, String anonymizedDateOfBirth, String zipCode, String gender, String disease) {
        this.anonymizedId = anonymizedId;
        this.anonymizedName = anonymizedName;
        this.anonymizedDateOfBirth = anonymizedDateOfBirth;
        this.zipCode = zipCode;
        this.gender = gender;
        this.disease = disease;
    }

    public String getAnonymizedName() {
        return anonymizedName;
    }

    public void setAnonymizedName(String anonymizedName) {
        this.anonymizedName = anonymizedName;
    }

    public String getAnonymizedDateOfBirth() {
        return anonymizedDateOfBirth;
    }

    public void setAnonymizedDateOfBirth(String anonymizedDateOfBirth) {
        this.anonymizedDateOfBirth = anonymizedDateOfBirth;
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

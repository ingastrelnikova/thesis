package example.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "anonymized_patients")
public class AnonymizedPatient {

    @Id
    private Long anonymizedId;
    private String anonymizedName;
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
// Constructors, getters, and setters
}

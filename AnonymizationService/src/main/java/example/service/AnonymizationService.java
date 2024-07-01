package example.service;

import example.dto.PatientDto;
import example.dto.AnonymizedPatientDto;
import example.entity.AnonymizedPatient;
import example.repository.AnonymizedPatientRepository;
import org.deidentifier.arx.*;
import org.deidentifier.arx.aggregates.HierarchyBuilderRedactionBased;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.Data.DefaultData;
import org.deidentifier.arx.aggregates.HierarchyBuilderDate;
import org.deidentifier.arx.aggregates.HierarchyBuilderDate.Granularity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class AnonymizationService {

    @Autowired
    private AnonymizedPatientRepository anonymizedPatientRepository;


    @Transactional
    public void deletePatientsByIds(List<Long> patientIds) {
        patientIds.forEach(id -> {
            Optional<AnonymizedPatient> patient = anonymizedPatientRepository.findById(id);
            if (patient.isPresent()) {
                anonymizedPatientRepository.delete(patient.get());
                System.out.println("Deleted patient with ID: " + id); // Log or handle the deletion notification
            } else {
                System.out.println("No patient found with ID: " + id); // Log or handle the case where ID is not found
            }
        });
    }

    public List<AnonymizedPatientDto> anonymizePatients(List<PatientDto> patients) {
        DefaultData data = createDataFromPatients(patients);
        ARXAnonymizer anonymizer = new ARXAnonymizer();
        ARXConfiguration config = ARXConfiguration.create();
        config.addPrivacyModel(new KAnonymity(2));
        config.setSuppressionLimit(0d);

        try {
            ARXResult result = anonymizer.anonymize(data, config);
            DataHandle handle = result.getOutput(false);
            if (handle == null) {
                System.out.println("Anonymization failed. The resulting data handle is null.");
                return new ArrayList<>();
            }
            List<AnonymizedPatientDto> anonymizedPatients = createAnonymizedPatientList(handle);
            saveAnonymizedPatients(anonymizedPatients);
            return anonymizedPatients;
        } catch (Exception e) {
            // Handle exceptions appropriately
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private DefaultData createDataFromPatients(List<PatientDto> patients) {
        DefaultData data = Data.create();
        data.add("id", "name", "dateOfBirth", "gender", "zipcode", "disease");

        // Add data rows
        patients.forEach(patient -> {
            data.add(patient.getId().toString(), patient.getName(), patient.getDateOfBirth(), patient.getGender(),
                    patient.getZipCode(), patient.getDisease());
        });

        // Set attribute types
        data.getDefinition().setAttributeType("name", AttributeType.INSENSITIVE_ATTRIBUTE);
        data.getDefinition().setAttributeType("name", AttributeType.IDENTIFYING_ATTRIBUTE);
        data.getDefinition().setAttributeType("disease", AttributeType.INSENSITIVE_ATTRIBUTE);
        data.getDefinition().setAttributeType("gender", AttributeType.INSENSITIVE_ATTRIBUTE);

        // Configure hierarchies
        HierarchyBuilderDate dateHierarchy = getDateOfBirthHierarchy();
        if (dateHierarchy == null) {
            System.out.println("Failed to build date hierarchy.");
        }
        //data.getDefinition().setAttributeType("dateOfBirth", dateHierarchy);
        data.getDefinition().setAttributeType("zipcode", getZipCodeHierarchy());
        return data;
    }

//    private HierarchyBuilderDate getDateOfBirthHierarchy() {
//        String stringDateFormat = "yyyy-MM-dd";
//        DataType<Date> dateType = DataType.createDate(stringDateFormat);
//
//        // Create the builder
//        HierarchyBuilderDate builder = HierarchyBuilderDate.create(dateType);
//
//        // Define grouping
//        builder.setGranularities(new Granularity[]{
//                Granularity.WEEK_YEAR,
//                Granularity.QUARTER_YEAR,
//                Granularity.YEAR
//        });
//        // Build the hierarchy
//        return builder;
//    }
private HierarchyBuilderDate getDateOfBirthHierarchy() {
    try {
        String stringDateFormat = "yyyy-MM-dd";
        DataType<Date> dateType = DataType.createDate(stringDateFormat);

        // Create the builder
        HierarchyBuilderDate builder = HierarchyBuilderDate.create(dateType);

        // Define grouping
        builder.setGranularities(new HierarchyBuilderDate.Granularity[]{
                HierarchyBuilderDate.Granularity.WEEK_YEAR,
                HierarchyBuilderDate.Granularity.QUARTER_YEAR,
                HierarchyBuilderDate.Granularity.YEAR
        });

        // Prepare the builder with the date range

        // Build the hierarchy

        return builder;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}



    private HierarchyBuilderRedactionBased<String> getZipCodeHierarchy() {
        HierarchyBuilderRedactionBased<String> zipBuilder = HierarchyBuilderRedactionBased.create(
                HierarchyBuilderRedactionBased.Order.RIGHT_TO_LEFT,
                HierarchyBuilderRedactionBased.Order.RIGHT_TO_LEFT,
                ' ',
                '*');
        return zipBuilder;
    }

    private List<AnonymizedPatientDto> createAnonymizedPatientList(DataHandle handle) {
        List<AnonymizedPatientDto> result = new ArrayList<>();
        for (int i = 0; i < handle.getNumRows(); i++) {
            result.add(new AnonymizedPatientDto(
                    Long.parseLong(handle.getValue(i, 0)),   // Anonymized id
                    handle.getValue(i, 1),   // Anonymized name
                    handle.getValue(i, 2),   // Anonymized dateOfBirth as LocalDate
                    handle.getValue(i, 3),   // Anonymized zip
                    handle.getValue(i, 4),   // Gender (insensitive, not anonymized)
                    handle.getValue(i, 5)    // Anonymized disease
            ));
        }
        return result;
    }

    private void saveAnonymizedPatients(List<AnonymizedPatientDto> anonymizedPatientDtos) {
        List<AnonymizedPatient> anonymizedPatients = new ArrayList<>();
        for (AnonymizedPatientDto dto : anonymizedPatientDtos) {
            AnonymizedPatient anonymizedPatient = new AnonymizedPatient();
            anonymizedPatient.setAnonymizedId(dto.getAnonymizedId());
            anonymizedPatient.setAnonymizedName(dto.getAnonymizedName());
            anonymizedPatient.setAnonymizedDateOfBirth(dto.getAnonymizedDateOfBirth()); // Save as LocalDate
            anonymizedPatient.setZipCode(dto.getZipCode());
            anonymizedPatient.setGender(dto.getGender());
            anonymizedPatient.setDisease(dto.getDisease());
            anonymizedPatients.add(anonymizedPatient);
        }
        anonymizedPatientRepository.saveAll(anonymizedPatients);
    }
}

package example.repository;


import example.entity.AnonymizedPatient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymizedPatientRepository extends JpaRepository<AnonymizedPatient, Long> {
}

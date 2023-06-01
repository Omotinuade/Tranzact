package africa.semicolon.demo.repository;

import africa.semicolon.demo.models.BioData;
import africa.semicolon.demo.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiodataRepository extends JpaRepository<BioData,Long> {
}

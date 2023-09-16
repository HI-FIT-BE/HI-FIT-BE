package hifit.be.user.repository;

import hifit.be.user.entity.HealthInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthInformationRepository extends JpaRepository<HealthInformation, Long> {

    Optional<HealthInformation> findByUserId(Long id);
}

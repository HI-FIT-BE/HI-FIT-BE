package hifit.be.user.repository;

import hifit.be.user.entity.HealthInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthInformationRepository extends JpaRepository<HealthInformation, Long> {
}

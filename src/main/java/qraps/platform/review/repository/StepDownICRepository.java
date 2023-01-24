package qraps.platform.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qraps.platform.review.entity.StepDownIC;

import java.util.Optional;

public interface StepDownICRepository extends JpaRepository<StepDownIC, String> {
    Optional<StepDownIC> findByType(String type);

    Optional<StepDownIC> findByPartNo(String partNo);
}

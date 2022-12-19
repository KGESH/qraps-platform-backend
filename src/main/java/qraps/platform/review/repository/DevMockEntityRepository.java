package qraps.platform.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qraps.platform.review.entity.DevMockEntity;

import java.util.Optional;
import java.util.UUID;

public interface DevMockEntityRepository extends JpaRepository<DevMockEntity, UUID> {
    Optional<DevMockEntity> findByName(String name);
}

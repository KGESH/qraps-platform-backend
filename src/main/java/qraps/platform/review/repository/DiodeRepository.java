package qraps.platform.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qraps.platform.review.entity.Diode;

public interface DiodeRepository extends JpaRepository<Diode, String> {
}

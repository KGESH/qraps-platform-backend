package qraps.platform.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qraps.platform.review.entity.Transistor;

public interface TransistorRepository extends JpaRepository<Transistor, String> {
}

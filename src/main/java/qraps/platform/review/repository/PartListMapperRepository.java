package qraps.platform.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qraps.platform.review.entity.PartList;

import java.util.List;
import java.util.Optional;

public interface PartListMapperRepository extends JpaRepository<PartList, String> {
    Optional<List<PartList>> findAllByDevice(int device);

    Optional<PartList> findByPartNo(String partNo);

}

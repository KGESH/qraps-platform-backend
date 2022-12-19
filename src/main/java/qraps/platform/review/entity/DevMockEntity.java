package qraps.platform.review.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import qraps.platform.review.dto.ReviewDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DevMockEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column
    String name;

    @Column
    String valueOne;

    @Column
    String valueTwo;

    @Column
    String valueThree;

    @Column
    String valueFour;

    /**
     * Todo: impl
     */
    public ReviewDto.Result review(ReviewDto.CsvPositionMapper dto) {
        return null;
    }

}

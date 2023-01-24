package qraps.platform.review.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity(name = "partlist")
public class PartList {

    @Id
    @Column(name = "partNo")
    private String partNo;

    @Column(name = "device")
    private int device;

    public PartList() {

    }

}

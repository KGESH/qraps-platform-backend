package qraps.platform.review.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity(name = "diode")
public class Diode {
    @Id
    @Column(name = "partNo")
    private String partNo;

    @Column(name = "type")
    private String type;

    @Column(name = "manufacturer_name")
    private String manufacturer_name;

    @Column(name = "oprating_temperature_min")
    private Double oprating_temperature_min;
    @Column(name = "oprating_temperature_max")
    private Double oprating_temperature_max;
    @Column(name = "storage_temperature_min")
    private Double storage_temperature_min;
    @Column(name = "storage_temperature_max")
    private Double storage_temperature_max;

    @Column(name = "juction_temperature_min")
    private Double juction_temperature_min;
    @Column(name = "juction_temperature_max")
    private Double juction_temperature_max;

    @Column(name = "forward_current_max")
    private Double forward_current_max;
    @Column(name = "non_repated_peak_Surge_current_max")
    private Double non_repated_peak_Surge_current_max;
    @Column(name = "voltage_rate_of_change")
    private Double voltage_rate_of_change;
    @Column(name = "peak_repetitive_reverse_voltage_max")
    private Double peak_repetitive_reverse_voltage_max;
    @Column(name = "peak_reverse_voltage_max")
    private Double peak_reverse_voltage_max;
    @Column(name = "capacitance_min")
    private Double capacitance_min;
    @Column(name = "capacitance_typ")
    private Double capacitance_typ;
    @Column(name = "capacitance_max")
    private Double capacitance_max;
    @Column(name = "reverse_recovery_time")
    private Double reverse_recovery_time;
    @Column(name = "reliability_msl")
    private Double reliability_msl;
    @Column(name = "reliability_rohs")
    private Double reliability_rohs;
    @Column(name = "reliability_pbf")
    private Double reliability_pbf;
    @Column(name = "reliability_halogen_free")
    private Double reliability_halogen_free;
    @Column(name = "reliability_aec_q101")
    private Double reliability_aec_q101;
    @Column(name = "package_type")
    private String package_type;

    @Column(name = "package_pin_num")
    private Double package_pin_num;
    @Column(name = "diode_quantity")
    private Double diode_quantity;
    @Column(name = "cathod_pin")
    private Double cathod_pin;
    @Column(name = "anode_pin")
    private Double anode_pin;
    @Column(name = "picture1")
    private String picture1;
    @Column(name = "picture2")
    private String picture2;
    @Column(name = "picture3")
    private String picture3;

}

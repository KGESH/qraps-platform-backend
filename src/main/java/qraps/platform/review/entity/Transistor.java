package qraps.platform.review.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "bjt")
@Getter
public class Transistor {
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


    @Column(name = "collector_emitter_voltage")
    private Double collector_emitter_voltage;

    @Column(name = "collector_base_voltage")
    private Double collector_base_voltage;

    @Column(name = "emitter_base_voltage")
    private Double emitter_base_voltage;

    @Column(name = "collector_current")
    private Double collector_current;

    @Column(name = "base_cutoff_current_max")
    private Double base_cutoff_current_max;

    @Column(name = "collector_cutoff_current_max")
    private Double collector_cutoff_current_max;

    @Column(name = "dc_current_gain_min")
    private Double dc_current_gain_min;

    @Column(name = "dc_current_gain_max")
    private Double dc_current_gain_max;

    @Column(name = "collector_emitter_saturation_voltage_max")
    private Double collector_emitter_saturation_voltage_max;

    @Column(name = "current_gain_bandwidth_product_min")
    private Double current_gain_bandwidth_product_min;
    @Column(name = "hfe_min")
    private Double hfe_min;
    @Column(name = "hfe_max")
    private Double hfe_max;
    @Column(name = "delay_time_max")
    private Double delay_time_max;
    @Column(name = "rise_time_max")
    private Double rise_time_max;
    @Column(name = "fall_time_max")
    private Double fall_time_max;
    @Column(name = "reliability_msl")
    private Double reliability_msl;
    @Column(name = "reliability_rohs")
    private Double reliability_rohs;
    @Column(name = "reliability_pbf")
    private Double reliability_pbf;
    @Column(name = "reliability_halogen_free")
    private Double reliability_halogen_free;
    @Column(name = "reliability_aec")
    private Double reliability_aec;
    @Column(name = "package_type")
    private String package_type;
    @Column(name = "package_pin_num")
    private Double package_pin_num;
    @Column(name = "tr_num")
    private Double tr_num;
    @Column(name = "emitter_pin")
    private Double emitter_pin;
    @Column(name = "base_pin")
    private Double base_pin;
    @Column(name = "collector_pin")
    private Double collector_pin;
    @Column(name = "base_resistor")
    private Double base_resistor;
    @Column(name = "base_zener")
    private Double base_zener;
    @Column(name = "Darlington")
    private Double Darlington;
    @Column(name = "picture1")
    private String picture1;
    @Column(name = "picture2")
    private String picture2;
    @Column(name = "picture3")
    private String picture3;
}

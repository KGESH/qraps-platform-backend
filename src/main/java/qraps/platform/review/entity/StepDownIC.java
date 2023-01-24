package qraps.platform.review.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "sdic")
public class StepDownIC {
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


    @Column(name = "input_voltage_min")
    private Double input_voltage_min;
    @Column(name = "input_voltage_max")
    private Double input_voltage_max;


    @Column(name = "output_voltage_min")
    private Double output_voltage_min;
    @Column(name = "output_voltage_max")
    private Double output_voltage_max;

    @Column(name = "output_voltage_tolerance_plus")
    private Double output_voltage_tolerance_plus;
    @Column(name = "output_voltage_tolerance_minus")
    private Double output_voltage_tolerance_minus;

    @Column(name = "output_current_min")
    private Double output_current_min;
    @Column(name = "output_current_max")
    private Double output_current_max;

    @Column(name = "dropout_voltage_min")
    private Double dropout_voltage_min;
    @Column(name = "dropout_voltage_max")
    private Double dropout_voltage_max;

    @Column(name = "quiescent_current_min")
    private Double quiescent_current_min;
    @Column(name = "quiescent_current_typ")
    private Double quiescent_current_typ;

    @Column(name = "quiescent_current_max")
    private Double quiescent_current_max;

    @Column(name = "efficiency_min")
    private Double efficiency_min;
    @Column(name = "efficiency_typ")
    private Double efficiency_typ;

    @Column(name = "efficiency_max")
    private Double efficiency_max;

    @Column(name = "oscillation_frequency_min")
    private Double oscillation_frequency_min;
    @Column(name = "oscillation_frequency_typ")
    private Double oscillation_frequency_typ;

    @Column(name = "oscillation_frequency_max")
    private Double oscillation_frequency_max;


    @Column(name = "current_limit_min")
    private Double current_limit_min;
    @Column(name = "current_limit_typ")
    private Double current_limit_typ;

    @Column(name = "current_limit_max")
    private Double current_limit_max;


    @Column(name = "thermal_shutdown_typ")
    private Double thermal_shutdown_typ;
    @Column(name = "thermal_recovery_typ")
    private Double thermal_recovery_typ;
    @Column(name = "reliability_esd_hbm_plus")
    private Double reliability_esd_hbm_plus;
    @Column(name = "reliability_esd_hbm_minus")
    private Double reliability_esd_hbm_minus;

    @Column(name = "reliability_esd_cdm_plus")
    private Double reliability_esd_cdm_plus;
    @Column(name = "reliability_esd_cdm_minus")
    private Double reliability_esd_cdm_minus;
    @Column(name = "reliability_surge")
    private Double reliability_surge;

    @Column(name = "reliability_eft")
    private Double reliability_eft;

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

    @Column(name = "feedback")
    private Double feedback;
    @Column(name = "enable")
    private Double enable;
    @Column(name = "pad")
    private Double pad;
    @Column(name = "boot")
    private Double boot;

    @Column(name = "picture1")
    private String picture1;
    @Column(name = "picture2")
    private String picture2;
    @Column(name = "picture3")
    private String picture3;
    @Column(name = "picture4")
    private String picture4;
    @Column(name = "picture5")
    private String picture5;

    public StepDownIC() {
    }
}

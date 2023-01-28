USE pdr;
DROP TABLE IF EXISTS sdic;
CREATE TABLE sdic
(
    partNo                         VARCHAR(20),
    type                           VARCHAR(20),
    manufacturer_name              VARCHAR(20),
    oprating_temperature_min       INT,
    oprating_temperature_max       INT,
    storage_temperature_min        INT,
    storage_temperature_max        INT,
    juction_temperature_min        INT,
    juction_temperature_max        INT,
    input_voltage_min              DECIMAL(5, 1),
    input_voltage_max              DECIMAL(5, 1),
    output_voltage_min             DECIMAL(4, 1),
    output_voltage_max             DECIMAL(5, 1),
    output_voltage_tolerance_plus  DECIMAL(3, 1),
    output_voltage_tolerance_minus DECIMAL(3, 1),
    output_current_min             DECIMAL(3, 1),
    output_current_max             DECIMAL(5, 1),
    dropout_voltage_min            DECIMAL(5, 2),
    dropout_voltage_max            DECIMAL(5, 2),
    quiescent_current_min          DECIMAL(9, 3),
    quiescent_current_typ          DECIMAL(9, 3),
    quiescent_current_max          DECIMAL(9, 3),
    efficiency_min                 DECIMAL(4, 1),
    efficiency_typ                 DECIMAL(4, 1),
    efficiency_max                 DECIMAL(4, 1),
    oscillation_frequency_min      DECIMAL(7, 1),
    oscillation_frequency_typ      DECIMAL(7, 1),
    oscillation_frequency_max      DECIMAL(7, 1),
    current_limit_min              DECIMAL(5, 1),
    current_limit_typ              DECIMAL(5, 1),
    current_limit_max              DECIMAL(5, 1),
    thermal_shutdown_typ           INT,
    thermal_recovery_typ           INT,
    reliability_esd_hbm_plus       INT,
    reliability_esd_hbm_minus      INT,
    reliability_esd_cdm_plus       INT,
    reliability_esd_cdm_minus      INT,
    reliability_surge              INT,
    reliability_eft                INT,
    reliability_msl                INT(1) CHECK (reliability_msl >= 0 AND reliability_msl <= 5),
    reliability_rohs               INT(1) CHECK (reliability_rohs = 1 OR reliability_rohs = 0),
    reliability_pbf                INT(1) CHECK (reliability_pbf = 1 OR reliability_pbf = 0),
    reliability_halogen_free       INT(1) CHECK (reliability_halogen_free = 1 OR reliability_halogen_free = 0),
    reliability_aec                INT(1) CHECK (reliability_aec = 1 OR reliability_aec = 0),
    package_type                   VARCHAR(50),
    package_pin_num                INT,
    feedback                       INT(1) CHECK (feedback = 1 OR feedback = 0),
    enable                         INT(1) CHECK (enable = 1 OR enable = 0),
    pad                            INT(1) CHECK (pad = 1 OR pad = 0),
    boot                           INT(1) CHECK (boot = 1 OR boot = 0),
    picture1                       VARCHAR(50),
    picture2                       VARCHAR(50),
    picture3                       VARCHAR(50),
    picture4                       VARCHAR(50),
    picture5                       VARCHAR(50)
);
INSERT INTO sdic
VALUES ('sdic', 5, 0,
        1, 3, 1, 3, 1, 3, 1, 3, 1, 3,
        3, 3,
        1, 3, 1, 3,
        1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2, 3,
        4, 4,
        3, 3, 3, 3,
        3,
        0, 0, 0, 0, 0, 0,
        0,
        0, 0, 0, 0, 0,
        NULL, NULL, NULL, NULL, NULL);
INSERT INTO sdic
VALUES ('LM2576HVSX-ADJ/NOPB', 'step_down', 'TI',
        -40, 125, -65, 150, NULL, 150, 3.5, 60.0, 1.2, 57.0,
        4.0, 4.0,
        NULL, 3.0, 1.60, 1.70,
        NULL, 5.000, 12.000, NULL, 77.0, NULL, 46.8, 52.0, 57.2, 3.5, 5.8, 6.9,
#         100, 100,
        NULL, NULL,
        2000, 2000, NULL, NULL,
        NULL,
        NULL, 1, 1, 1, NULL, 0,
        '/pdrpic/1sdic02p0.png',
        5, NULL, NULL, NULL, NULL,
        '/pdrpic/1sdic02p1.png', '/pdrpic/1sdic02p2.png', '/pdrpic/1sdic02p3.png', '/pdrpic/1sdic02p4.png',
        '/pdrpic/1sdic02p5.png');
SELECT *
FROM sdic;

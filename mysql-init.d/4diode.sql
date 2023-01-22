USE pdr;
DROP TABLE IF EXISTS diode;
CREATE TABLE diode
(
    partNo                              VARCHAR(20),
    type                                VARCHAR(20),
    manufacturer_name                   VARCHAR(20),
    oprating_temperature_min            INT,
    oprating_temperature_max            INT,
    storage_temperature_min             INT,
    storage_temperature_max             INT,
    juction_temperature_min             INT,
    juction_temperature_max             INT,
    forward_current_max                 DECIMAL(6, 2),
    non_repated_peak_Surge_current_max  DECIMAL(6, 2),
    voltage_rate_of_change              DECIMAL(9, 2),
    peak_repetitive_reverse_voltage_max DECIMAL(7, 2),
    peak_reverse_voltage_max            DECIMAL(7, 2),
    capacitance_min                     DECIMAL(6, 2),
    capacitance_typ                     DECIMAL(6, 2),
    capacitance_max                     DECIMAL(6, 2),
    reverse_recovery_time               DECIMAL(10, 3),
    reliability_msl                     INT(1) CHECK (reliability_msl >= 1 AND reliability_msl <= 5),
    reliability_rohs                    INT(1) CHECK (reliability_rohs = 1 OR reliability_rohs = 0),
    reliability_pbf                     INT(1) CHECK (reliability_pbf = 1 OR reliability_pbf = 0),
    reliability_halogen_free            INT(1) CHECK (reliability_halogen_free = 1 OR reliability_halogen_free = 0),
    reliability_aec_q101                INT(1) CHECK (reliability_aec_q101 = 1 OR reliability_aec_q101 = 0),
    package_type                        VARCHAR(50),
    package_pin_num                     INT,
    diode_quantity                      INT,
    cathod_pin                          INT,
    anode_pin                           INT,
    picture1                            VARCHAR(50),
    picture2                            VARCHAR(50),
    picture3                            VARCHAR(50)
);
INSERT INTO diode
VALUES ('diode', 3, 0,
        1, 3, 1, 3, 1, 3,
        3, 3,
        0,
        3, 3,
        1, 2, 3,
        0,
--      reliability_msl CHECK Constraint 1 < 5
        NULL, 0, 0, 0, 0,
        0,
        0, 0, 0, 0,
        NULL, NULL, NULL);
INSERT INTO diode
VALUES ('BAV16W/1N4148W', 'fast_switching', 'DIODES',
        -55, 150, -55, 155, NULL, NULL,
        0.30,
        NULL, NULL,
        100.00, NULL,
        NULL, 2.00, NULL,
        4.000,
        1, 1, 1, NULL, 1,
        '/pdrpic/3diode02p0.png',
        2, 1, 1, 2,
        '/pdrpic/3diode02p1.png', '/pdrpic/3diode02p2.png', '/pdrpic/3diode02p3.png');
SELECT *
FROM diode;

USE pdr;
DROP TABLE IF EXISTS bjt;
CREATE TABLE bjt
(
    partNo                                   VARCHAR(20),
    type                                     VARCHAR(20),
    manufacturer_name                        VARCHAR(20),
    oprating_temperature_min                 INT,
    oprating_temperature_max                 INT,
    storage_temperature_min                  INT,
    storage_temperature_max                  INT,
    juction_temperature_min                  INT,
    juction_temperature_max                  INT,
    collector_emitter_voltage                DECIMAL(5, 1),
    collector_base_voltage                   DECIMAL(5, 1),
    emitter_base_voltage                     DECIMAL(5, 1),
    collector_current                        DECIMAL(7, 3),
    base_cutoff_current_max                  DECIMAL(10, 3),
    collector_cutoff_current_max             DECIMAL(10, 3),
    dc_current_gain_min                      INT,
    dc_current_gain_max                      INT,
    collector_emitter_saturation_voltage_max DECIMAL(5, 2),
    current_gain_bandwidth_product_min       DECIMAL(7, 1),
    hfe_min                                  INT,
    hfe_max                                  INT,
    delay_time_max                           DECIMAL(7, 1),
    rise_time_max                            DECIMAL(7, 1),
    fall_time_max                            DECIMAL(7, 1),
    reliability_msl                          INT(1) CHECK (reliability_msl >= 1 AND reliability_msl <= 5),
    reliability_rohs                         INT(1) CHECK (reliability_rohs = 1 OR reliability_rohs = 0),
    reliability_pbf                          INT(1) CHECK (reliability_pbf = 1 OR reliability_pbf = 0),
    reliability_halogen_free                 INT(1) CHECK (reliability_halogen_free = 1 OR reliability_halogen_free = 0),
    reliability_aec                          INT(1) CHECK (reliability_aec = 1 OR reliability_aec = 0),
    package_type                             VARCHAR(50),
    package_pin_num                          INT,
    tr_num                                   INT,
    emitter_pin                              INT,
    base_pin                                 INT,
    collector_pin                            INT,
    base_resistor                            INT(1) CHECK (base_resistor = 1 OR base_resistor = 0),
    base_zener                               INT(1) CHECK (base_zener = 1 OR base_zener = 0),
    Darlington                               INT(1) CHECK (Darlington = 1 OR Darlington = 0),
    picture1                                 VARCHAR(50),
    picture2                                 VARCHAR(50),
    picture3                                 VARCHAR(50)
);
INSERT INTO bjt
VALUES ('bjt', 3, 0,
        1, 3, 1, 3, 1, 3,
        3, 3, 3, 0,
        3, 3,
        1, 3,
        3,
        1,
        1, 3,
        3, 3, 3,
--       reliability_msl CHECK Constraint 1 < 5
        NULL, 0, 0, 0, 0,
        0,
        0, 0, 0, 0, 0, 0, 0, 0,
        NULL, NULL, NULL);
INSERT INTO bjt
VALUES ('2N3906', 'pnp_general_purpose', 'on_semiconductor',
        -55, 150, -55, 155, NULL, NULL,
        40.0, 40.0, 5.0, 0.200,
        50.000, 50.000,
        100, 300,
        0.40,
        250.0,
        100, 400,
        35.0, 35.0, 75.0,
        NULL, NULL, NULL, NULL, NULL,
        '/pdrpic/2bjt02p0.png',
        3, 1, 1, 2, 3, 0, 0, 0,
        '/pdrpic/2bjt02p1.png', '/pdrpic/2bjt02p2.png', '/pdrpic/2bjt02p3.png');
SELECT *
FROM bjt;

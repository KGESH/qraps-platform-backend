DROP
DATABASE IF EXISTS pdr;
CREATE
DATABASE pdr	/*!40100 COLLATE 'utf8_general_ci' */;
USE
pdr;
DROP TABLE IF EXISTS partlist;
CREATE TABLE partlist
(
    partNo VARCHAR(20),
    device INT CHECK (device >= 1 AND device <= 3)
);
INSERT INTO partlist (partNo, device)
VALUES ('ACT8310NHADJ', 1),
       ('AP2112K-3.3TRG1', 1),
       ('APW8743CQ', 1),
       ('LM2575', 1),
       ('LM2576', 1),
       ('ME6211', 1),
       ('MP2565', 1),
       ('NB692GD-Z', 1),
       ('RT7276GQW', 1),
       ('TMI3113', 1),
       ('TPS62260TDRVRQ1', 1),
       ('2N3906', 2),
       ('BC847', 2),
       ('DTC114EKA', 2),
       ('HQ8001', 2),
       ('KRC101S', 2),
       ('KTN2222AS', 2),
       ('MMBF170', 2),
       ('MMBT2222A', 2),
       ('MMBT2907A', 2),
       ('MMBT3904', 2),
       ('NSVBT2222', 2),
       ('PMBS3904', 2),
       ('STR1550', 2),
       ('STR2550', 2),
       ('TKR101S', 2),
       ('1N4001-T', 3),
       ('1N4007', 3),
       ('1N4148', 3),
       ('1N5400', 3),
       ('1N5822', 3),
       ('BAT54AW', 3),
       ('BAT55CW', 3),
       ('BAV99', 3),
       ('DF06', 3),
       ('ES1J', 3),
       ('FDLL4148', 3),
       ('KBP310', 3),
       ('KDS4148U', 3),
       ('KOS160E', 3),
       ('LL4148', 3),
       ('MB-6S', 3),
       ('MBR0520L', 3),
       ('MBRF20200CT', 3),
       ('PC817D', 3),
       ('RGP02-20E-E3/54', 3),
       ('RS1M', 3),
       ('S1GHE', 3),
       ('SS14', 3),
       ('SS16', 3),
       ('SS24', 3),
       ('SS34', 3),
       ('UF4007', 3),
       ('UF5404', 3);
SELECT *
FROM partlist;

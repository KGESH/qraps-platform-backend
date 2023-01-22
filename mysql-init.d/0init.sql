CREATE
USER 'example'@'localhost' IDENTIFIED BY 'example';
CREATE
USER 'example'@'%' IDENTIFIED BY 'example';

GRANT ALL PRIVILEGES ON *.* TO
'example'@'localhost';
GRANT ALL PRIVILEGES ON *.* TO
'example'@'%';


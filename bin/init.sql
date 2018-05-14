CREATE DATABASE if not exists `paccipiter` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

use mysql;

CREATE USER 'paccipiter'@'localhost' IDENTIFIED BY 'paccipiter' PASSWORD EXPIRE NEVER;

UPDATE user SET authentication_string= password ('48STX2X') WHERE user='root';
flush privileges;
grant all privileges on paccipiter.* to 'paccipiter'@'%' identified by 'paccipiter';
grant all privileges on paccipiter.* to 'paccipiter'@'localhost' identified by 'paccipiter';
grant all privileges on paccipiter.* to 'paccipiter'@'127.0.0.1' identified by 'paccipiter';

flush privileges;
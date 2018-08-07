
CREATE DATABASE `cloudpan`;

USE `cloudpan`;

# CREATE TABLE `file` (
#   `file_id` BIGINT unsigned NOT NULL auto_increment,
#   `file_name` varchar(255) NOT NULL COMMENT '文件名',
#   `file_path` varchar(600) NOT NULL COMMENT '文件路径',
#   `file_size` long NOT NULL COMMENT '文件大小',
#   `create_time` TIMESTAMP COMMENT '创建日期',
#   `share` int(2) NOT NULL DEFAULT 0 COMMENT '0表示私有 1表示共享',
#   PRIMARY KEY  (`file_id`),
#   KEY idx_create_time(create_time)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#有个坑：本来想在user中使用联合主键，结果这样会导致record建立不了外键索引，遂放弃


CREATE TABLE `user` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT UNIQUE ,
  `user_phone` BIGINT NOT NULL UNIQUE ,
  `user_name` varchar(255) NOT NULL,
  `user_pwd` char(32) NOT NULL,
  `isvip` int(11) NOT NULL DEFAULT 0 COMMENT '1是vip 0不是',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO user (user_phone, user_name, user_pwd)
VALUES
  (13996936879, 'tracy', 'admin');


CREATE TABLE cloudpan.file
(
  file_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  file_path VARCHAR(255) NOT NULL,
  file_md5 CHAR(32) NOT NULL,
  file_size BIGINT NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX idx_file_md5 ON cloudpan.file (file_id);
ALTER TABLE cloudpan.file COMMENT = '实体文件表';


CREATE TABLE cloudpan.record
(
  record_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  file_id BIGINT NOT NULL,
  custom_name VARCHAR(255) NOT NULL,
  share SMALLINT DEFAULT 0,
  create_time TIMESTAMP NOT NULL ,
  CONSTRAINT record_user__fk FOREIGN KEY (user_id) REFERENCES user(user_id),
  CONSTRAINT record_file__fk FOREIGN KEY (file_id) REFERENCES file(file_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE cloudpan.record COMMENT = '网盘记录表';
alter table record add constraint uk_t_1 unique (file_id,user_id);
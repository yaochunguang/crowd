# 创建数据库
CREATE DATABASE project_crowd;

#使用数据库
USE project_crowd;

# 管理员表
CREATE TABLE `t_admin` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
    `login_acct` VARCHAR (255) NOT NULL COMMENT '登录账号',
    `user_pswd` CHAR(32) NOT NULL COMMENT '登录密码',
    `user_name` VARCHAR (255) NOT NULL COMMENT '昵称',
    `email` VARCHAR (255) NOT NULL COMMENT '邮件地址',
    `create_time` CHAR(19) COMMENT '创建时间 ',
    PRIMARY KEY (`id`)
);
# 增加唯一约束
ALTER TABLE `t_admin` ADD UNIQUE INDEX (`login_acct`);

# 角色表
CREATE TABLE `t_role` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键' ,
    `name` CHAR(100) NOT NULL COMMENT '角色名称'
);

# 菜单表
CREATE TABLE `t_menu` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` INT(11) DEFAULT NULL COMMENT '父id',
  `NAME` VARCHAR(200) DEFAULT NULL COMMENT '菜单名称',
  `url` VARCHAR(200) DEFAULT NULL COMMENT '菜单url',
  `icon` VARCHAR(200) DEFAULT NULL COMMENT '菜单图标',
  PRIMARY KEY (`id`)
);

# 初始化菜单数据
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('1',NULL,'系统权限菜单','','glyphiconglyphicon-th-list');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('2','1',' 控 制 面 板 ','main.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('3','1',' 权 限 管 理 ',NULL,'glyphicon glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('4','3',' 用 户 维 护 ','user/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('5','3',' 角 色 维 护 ','role/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('6','3',' 菜 单 维 护 ','permission/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('7','1',' 业 务 审 核 ',NULL,'glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('8','7','实名认证审核','auth_cert/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('9','7',' 广 告 审 核 ','auth_adv/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('10','7',' 项 目 审 核 ','auth_project/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('11','1',' 业 务 管 理 ',NULL,'glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('12','11',' 资 质 维 护 ','cert/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('13','11',' 分 类 管 理 ','certtype/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('14','11',' 流 程 管 理 ','process/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('15','11',' 广 告 管 理 ','advert/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('16','11',' 消 息 模 板 ','message/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('17','11',' 项 目 分 类 ','projectType/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('18','11',' 项 目 标 签 ','tag/index.htm','glyphicon');
insert into `t_menu` (`id`, `pid`, `NAME`, `url`, `icon`) values('19','1',' 参 数 管 理 ','param/index.htm','glyphicon');

# 管理员和角色的关联关系（权限）
# 这个表并不对应现实生活中或项目业务功能中的一个具体实体，所以没有对应 的实体类，也不通过逆向工程做逆向生成
CREATE TABLE `inner_admin_role` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `admin_id` INT COMMENT '管理员id',
  `role_id` INT COMMENT '角色id',
  PRIMARY KEY (`id`)
) ;

# 权限表
CREATE TABLE `t_auth` (
  `id` INT (11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` VARCHAR (200) DEFAULT NULL COMMENT '权限名称',
  `title` VARCHAR (200) DEFAULT NULL COMMENT '标题',
  `category_id` INT (11) DEFAULT NULL COMMENT '权限类别',
  PRIMARY KEY (`id`)
) ;

# 初始化权限表数据
INSERT INTO t_auth (id, `name`, title, category_id) VALUES(1, '', '用户模块', NULL) ;
INSERT INTO t_auth (id, `name`, title, category_id) VALUES(2, 'user:delete', '删除', 1) ;
INSERT INTO t_auth (id, `name`, title, category_id) VALUES(3, 'user:get', '查询', 1) ;
INSERT INTO t_auth (id, `name`, title, category_id) VALUES(4, '', '角色模块', NULL) ;
INSERT INTO t_auth (id, `name`, title, category_id) VALUES(5, 'role:delete', '删除', 4) ;
INSERT INTO t_auth (id, `name`, title, category_id) VALUES(6, 'role:get', '查询', 4) ;
INSERT INTO t_auth (id, `name`, title, category_id) VALUES(7, 'role:add', '新增', 4) ;

############ 前台会员系统
# 系统会员表
create table t_member (
id int(11) not null auto_increment,
loginacct varchar(255) not null comment '会员登录账号',
userpswd char(200) not null comment '登录密码',
username varchar(255)comment '用户名',
email varchar(255) comment '邮箱',
authstatus int(4) comment '认证状态',
usertype int(4) comment ' 0 - 个人， 1 - 企业',
realname varchar(255) comment '实名认证状态 0 - 未实名认证， 1 - 实名认证申 请中， 2 - 已实名认证',
cardnum varchar(255) comment '会员卡号',
accttype int(4) comment '0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府',
primary key (id) );
# 用户账号增加唯一索引
create unique index UK_loginacct on t_member (loginacct);
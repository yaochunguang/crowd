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

# 分类表
create table t_type ( id int(11) not null auto_increment,
name varchar(255) comment '分类名称',
remark varchar(255) comment '分类介绍',
primary key (id) );
# 基础数据
INSERT INTO t_type (name,remark) VALUES
('科技','科技创造未来')
,('设计','创意改变世界')
,('农业','网络天下肥美')
,('其他','发现更多惊喜')
;

# 项目分类中间表
create table t_project_type ( id int not null auto_increment,
projectid int(11),
typeid int(11),
primary key (id) );

# 标签表
create table t_tag ( id int(11) not null auto_increment,
pid int(11),
name varchar(255),
primary key (id) );

# 项目标签中间表
create table t_project_tag( id int(11) not null auto_increment,
projectid int(11),
tagid int(11),
primary key (id) );

# 项目表
create table t_project ( id int(11) not null auto_increment,
project_name varchar(255) comment '项目名称',
project_description varchar(255) comment '项目描述',
money bigint (11) comment '筹集金额',
day int(11) comment '筹集天数',
status int(4) comment '0-即将开始，1-众筹中，2-众筹成功，3-众筹失败 ',
deploydate varchar(10) comment '项目发起时间',
supportmoney bigint(11) comment '已筹集到的金额',
supporter int(11) comment '支持人数',
completion int(3) comment '百分比完成度',
memberid int(11) comment '发起人的会员 id',
createdate varchar(19) comment '项目创建时间',
follower int(11) comment '关注人数',
header_picture_path varchar(255) comment '头图路径',
primary key (id) );

# 项目表项目详情图片表
create table t_project_item_pic ( id int(11) not null auto_increment,
projectid int(11),
item_pic_path varchar(255),
primary key (id) );

# 项目发起人信息表
create table t_member_launch_info ( id int(11) not null auto_increment,
memberid int(11) comment '会员 id',
description_simple varchar(255) comment '简单介绍',
description_detail varchar(255) comment '详细介绍',
phone_num varchar(255) comment '联系电话',
service_num varchar(255) comment '客服电话',
primary key (id) );

# 回报信息表
create table t_return ( id int(11) not null auto_increment,
projectid int(11),
type int(4) comment '0 - 实物回报， 1 虚拟物品回报',
supportmoney int(11) comment '支持金额',
content varchar(255) comment '回报内容',
count int(11) comment '回报产品限额，“0”为不限回报数量',
signalpurchase int(11) comment '是否设置单笔限购',
purchase int(11) comment '具体限购数量',
freight int(11) comment '运费，“0”为包邮',
invoice int(4) comment '0 - 不开发票， 1 - 开发票',
returndate int(11) comment '项目结束后多少天向支持者发送回报',
describ_pic_path varchar(255) comment '说明图片路径',
primary key (id) );

# 发起人确认信息表
create table t_member_confirm_info ( id int(11) not null auto_increment,
memberid int(11) comment '会员 id',
paynum varchar(200) comment '易付宝企业账号',
cardnum varchar(200) comment '法人身份证号',
primary key (id) );
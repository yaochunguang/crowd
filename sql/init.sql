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
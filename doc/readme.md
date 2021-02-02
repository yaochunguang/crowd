# Crowd  -- 按照这种格式来说明项目

####  项目介绍

帮助创业者发布天使投资项目和凑集资本，分为前台会员系统和后台管理系统；

项目目的：从**单一架构**阶段到**分布式架构**阶段的过渡。后台管理员系统使用单一架构开发。前台会 

员系统使用分布式架构开发。

技术选型：

​	1）后台：Spring + SpringMVC + Mybatis后台框架；前端框架 layui zTree bootstrap 。

​    2）前台：SrpingBoot + Mybatis + SpringCloud; 前端框架,thymeleaf

> 基本组件
>
> 注册中心:  Eureka
>
> 负载均衡：Ribbon
>
> 声明式调用远程方法：Feign
>
> 熔断、降级、监控： Hystrix
>
> 网关：Zuul



#### 使用说明

5. 后台账号 http://localhost:8080/crowdadmin/admin/to/login/page.html
2. 账号：admin 密码 123456



### 项目架构

![image-20210202192049257](readme.assets/image-20210202192049257.png)

#### api网关设计

![输入图片说明](readme.assets/232938_7332bdee_634828.png)

### 部署说明

1. 建议采用 idea 工具开发
2. mysql 5.6+
3. jdk 1.8
4. tomcat 8 5.通过war包直接放webapps下面 



#### 前台会员系统：

##### 端口号约定

| 项目 | 端口号 |
| -------- | -------- |
| crowdfunding-member-eureka  | 1000 |
| crowdfunding-member-mysql-provider | 2000 |
| crowdfunding-member-redis-provider | 3000 |
| crowdfunding-member-authentication-consumer | 4000 |
| crowdfunding-member-project-consumer | 5000 |
| crowdfunding-member-order-consumer | 7000 |
| crowdfunding-member-pay-consumer | 8000 |
| crowdfunding-member-zuul | 80 |

#### 前台会员系统打包部署

> 在编译器的maven插件中执行clean->install生成部署文件；或者到项目pom文件所以目录执行mvn clean,install 来生成部署文件

![image-20210202161657962](readme.assets/image-20210202161657962.png)

> 会在target目录下的dist目录下生成一个xxx.tar.gz的部署包

![image-20210202161835058](readme.assets/image-20210202161835058.png)

>  把该部署文件上传到liunx服务器的部署目录;解压，执行start.sh即可启动项目

![image-20210202163507646](readme.assets/image-20210202163507646.png)

>  部署启动顺序： crowdfunding-member-eureka --> crowdfunding-member-mysql-provider --> 项目端口号crowdfunding-member-eureka1000crowdfunding-member-mysql-provider2000crowdfunding-member-redis-provider --> crowdfunding-member-authentication-consumer/crowdfunding-member-order-consumer/crowdfunding-member-pay-consumer --> crowdfunding-member-zuul
# springcloud-booksys
# 介绍
用springboot+thymeleaf实现图书馆借阅系统,前端网页ui使用网上下载的网页模板

系统具体划分为管理员和读者两个权限，为了使权限区分明显，最主要的图书借阅功能只有管理员能执行。具体功能划分如下：

用户身份决定具有的功能，以管理员登录具有以下功能：

(1)	增加新书
(2)	删除无用书
(3)	修改图书
(4)	增加、修改读者信息
(5)	把书借给读者
(6)	收回读者还的书
(7)	删除读者
(8)	对图书逾期未还的进行扣费
(9)	删除借阅信息
(10)查询读者信息
(11)查询借阅记录

以读者登录具有以下功能：

(1)	查看目前借入的书
(2)	查看借阅过的书
所有用户都具有以下功能：
(1)	按照不同条件查看书的信息
(2)	修改个人信息及密码

以下举例两种用户的登录，需要登录其他用户则要查询数据库：

管理员登录：

账号：0000001

密码：1234567或123456

读者登录：

账号:1000001

密码：1234567或123456

# 配置
git clone https://github.com/CherrySeason/springboot-booksys.git

需要修改pom.xml中
<artifactId>mysql-connector-java</artifactId>
<version>8.0.27</version>

mysql的依赖包为自己mysql的版本

springboot自动配置会装载依赖包，需要修改的地方是src/main/resources/application.properties中的数据库配置，需要改为适应本机的配置。

需要在服务提供者端的数据库中创建数据库Book，然后导入sql/data.sql文件运行，生成数据。

git clone下来不是maven项目，需要分别对consumer、euraka-sever、provider项目的pom.xml 文件上右键 Add as Maven Project，就可以改成mavern项目。

数据库：Mysql 8.0.27

开发平台：IntelliJ IDEA

开发集成环境：Windows 11，java jdk1.8, java 11

# 启动

需要按照以下顺序一次打开三个项目：EurakaSeverApplication、ProviderApplication、ConsumerApplication

在使用前需要打开redis，确认redis的端口是否是6379，并且如果有密码需要修改application.properties中redis的相关配置。

然后可以通过localhost:8200/login进行前端访问页面，如果登录时报系统错误，查看ConsumerApplication的报错后是如下情况：

![image](https://user-images.githubusercontent.com/78289616/170486976-bc985b4a-95f9-4801-bf66-55b4d74fca45.png)

说明是启动顺序出问题，一定要按照上述提及的顺序来进行启动！！最好是等ProviderApplication启动完后几秒后再启动ConsumerApplication。

# 前一版本

此为Springboot项目迭代后的版本，前一版本在https://github.com/CherrySeason/book-borrowing-system.git
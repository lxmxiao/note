# 配置JDK

* 首先，测试本地是否已经安装了jdk，利用java或javac命令来确认

* 未发现本机安装有jdk，此时，从oracle官网下载linux版本的jdk

* 将下载好的jdk上传到home目录下

* 将jdk所在的tar文件解压到 ~/java目录中

  tar -xvf jdk-8u141-linux-x64.tar.gz

* 将java目录移动到/usr/local/ 目录下

  sudo  mv  java /usr/local

* 在/etc/profile 中配置环境变量

  sudo vim /etc/profile

  ```properties
  JAVA_HOME=/usr/local/java/jdk1.8.0_141
  Path=$JAVA_HOME/bin:$PATH
  CLASSPATH=$CLASSPATH:.:$JAVA_HOME/lib:$JAVA_HOME/jre/lib
  export JAVAHOME PATH CLASSPATH
  ```

* 使配置文件立即生效，并测试配置的环境变量

  source /etc/profile

  java -version

# Tomcat 的安装

* 下载tomcat 8.x的linux版本的包 

* tomcat所在的tar文件，传到建目录下的tomcat文件夹中

* 解压并抽取该包

  tar -xvf apache-tomcat-8.5.16.tar.gz

* 将tomcat文件夹移动到/usr/local/目录下

* 启动tomcat，和windows中一样，主要是使用tomcat的bin目录中的startup工具

  在tomcat目录下的bin目录中 sh startup.sh

* 测试tomcat服务器，在windows上连接成功

* 关闭tomcat

  sh shutdown.sh

# MySQL 的安装

### 安装

* 首先检查是否本地有安装mysql

* 本地没有安装mysql，使用apt工具来安装mysql

  sudo apt-get install mysql-server

  会去服务器获取安装程序，并开始安装

  安装时需要设置MySQL的密码

* 启动mysql测试是否安装成功

### 配置远程访问

* 设置在远程访问linux的mysql，这个设置分为2个部分:

  * 第1部分是要修改mysql的配置文件，使得除本机之外的ip可以访问到数据库
  * 第2个部分是要在mysql中修改，给予root用户从别的主机上访问数据库的权限

  sudo vim /etc/mysql/mysql.conf.d/mysqld.cnf

  将其中的 bind-address 修改为 0.0.0.0，原本是 127.0.0.1 即本机

* 重启mysql服务，使配置生效

  sudo /etc/init.d/mysql restart

* 进入mysql，给远程连接授予权限

  mysql命令行中：grant all privileges on \*.\* to 'root'@'%' identified by '123456' with grant option;

* 查询设置的结果

  mysql命令行中：select user,host from mysql.user;

  ![image-20191125195721026](linux_Java%E7%8E%AF%E5%A2%83.assets/image-20191125195721026.png)

* 是配置文件立即生效

  mysql命令行中：flush privileges;

* 可以使用其他数据库连接程序在外部连接 linux 上的 mysql 服务器
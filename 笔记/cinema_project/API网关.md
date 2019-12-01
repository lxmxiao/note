# API网关

## 介绍

API 网关是一个服务器，是系统的唯一入口。从面向对象设计的角度看，它与外观模式类似。API 网关封装了系统内部架构，为每个客户端提供一个定制的 API 。它可能还具有其它职责，如身份验证、监控、负载均衡、缓存、请求分片与管理、静态响应处理。

API 网关方式的核心要点是，所有的客户端和消费端都通过统一的网关接入微服务，在网关层处理所有的非业务功能。通常，网关也是提供REST/HTTP的访问 API 。服务端通过 API-GateWay 注册和管理服务

## 架构图

![image-20191127152509539](API%E7%BD%91%E5%85%B3.assets/image-20191127152509539.png)

多入口：

![image-20191127152625950](API%E7%BD%91%E5%85%B3.assets/image-20191127152625950.png)

## 初步实现 搭架子

先把之前的 gunsrest 的 module 复制一个， 作为 rest 模块的架子

复制之后：

先到父工程去 增加子模块

```xml
<modules>
    <module>guns-admin</module>
    <module>guns-core</module>
    <module>guns-rest</module>
    <module>guns-generator</module>
    <module>guns-gateway</module>
</modules>
```

再到自己的模块进行修改

```xml
	<groupId>com.stylefeng.guns</groupId>
    <artifactId>guns-gateway</artifactId>
    <version>0.0.1</version>
    <packaging>jar</packaging>

    <name>guns-gateway</name>
    <description>guns 网关服务器</description>
```

复制之后需要引入 module ，修改完之后， maven 重新 import 这个项目就自动在了

然后在模块设置改一些东西

生成了自己的模块文件之后，当前模块里有两个

![image-20191127153110200](API%E7%BD%91%E5%85%B3.assets/image-20191127153110200.png)

![image-20191127153118974](API%E7%BD%91%E5%85%B3.assets/image-20191127153118974.png)

后面那个不属于自己的就可以删除掉了

## 引入 Dubbo

### 导包

```xml
<dependency>
   <groupId>com.alibaba.spring.boot</groupId>
   <artifactId>dubbo-spring-boot-starter</artifactId>
   <version>2.0.0</version>
</dependency>
<dependency>
   <groupId>com.101tec</groupId>
   <artifactId>zkclient</artifactId>
   <version>0.10</version>
</dependency>
```

### 修改配置文件

```yml
spring:
  application:
  	name: meeting-gateway
  dubbo:
  	server: true
  	registry: zookeeper://localhost:2181
```

### 启动类上增加注解

```java
@EnableDubboConfiguration
@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns"})
public class GunsRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GunsRestApplication.class, args);
    }
}
```

### 测试

启动 zookeeper

在测试代码中测试

# 项目平台整合 API 网关

## API 网关

![image-20191127153912369](API%E7%BD%91%E5%85%B3.assets/image-20191127153912369.png)

## 新建一个 Rest 模块，作为我们的 API 网关

## API 网关集成 Dubbo

## 抽离公共接口

### 引入

专门新建一个子工程，这个自工程的主要目的就是存放我们的业务接口，以及一些大家都需要用的一些模型

然后各个模块需要使用的话，就统一依赖这个子工程

### 实现

新建一个单独的子模块，比如叫guns-api

我们把上面的这种情况下需要使用的api接口以及一些大家都需要引用的类放在这个模块里。

然后install到maven仓库，在其他的模块里添加它作为依赖即可




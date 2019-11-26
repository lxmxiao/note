Maven
    |-- 简介
        Maven是Apache下的一个纯java开发的开源项目，它是一个项目管理构建工具，使用maven对java项目进行构建、依赖管理
    |-- 主要功能
        1.项目构建
        2.依赖管理(重点)
    |-- 项目构建
        项目构建是一个项目从编写源代码到编译、测试、打包、部署、运行的过程
        |-- maven工程构建的优点：
            1、一个命令完成构建、运行，方便快捷。
            2、maven对每个构建阶段进行规范，非常有利于大型团队协作开发。
    |-- 依赖管理
        对项目所有依赖的jar包进行规范化管理
        |-- 使用maven依赖管理添加jar的好处：
            1、通过pom.xml文件对jar包的版本进行统一管理，可避免版本冲突。
            2、maven团队维护了一个非常全的maven仓库，里边包括了当前使用的jar包，maven工程可以自动从maven仓库下载jar包，非常方便
    |-- 使用maven的好处
        1.一步构建
        2.依赖管理
        3.maven的跨平台性，可在window、linux上使用
        4.maven遵循规范开发有利于提高大型团队的开发效率，降低项目的维护成本，大公司都会考虑使用maven来构建项目
    |-- maven的结构目录(重点)
        |-src
        |   |-main
        |   |  |-java        —— 存放项目的.java文件
        |   |  |-resources   —— 存放项目资源文件，如spring, hibernate配置文件
        |   |  |-webapp   —— web项目的webroot文件夹，存放网页资源 如jsp等
        |   |-test
        |   |    |-java        ——存放所有测试.java文件，如JUnit测试类
        |   |    |-resources   —— 测试资源文件
        |-target             —— 目标文件输出位置例如.class、.jar、.war文件(会生成)
        |-pom.xml           ——maven项目核心配置文件
    |-- maven命令
        |-- mvn compile
            编译
            (默认编译src/main/java，同时会把src/resources下的资源放在编译的输出目录target/classes)
        |-- mvn clean
            执行完毕后，会将target目录删除
        |-- mvn test
            完成单元测试操作
            会在target目录中生成两个个文件夹： surefire-reports(测试报告)、test-classes(测试的字节码文件，包括test/resources的资源文件)
        |-- mvn package
            完成打包操作(在打包之前会执行mvn test)
            执行完毕后，会在target目录中生成一个文件，该文件可能是jar、war(默认是jar包)
        |-- mvn install
            将打好的jar包安装到本地仓库的操作
        |-- 组合指令:
            |-- mvn clean compile
                组合指令，先执行clean，再执行compile，通常应用于上线前执行，清除测试类
            |-- mvn clean test
                组合指令，先执行clean，再执行test，通常应用于测试环节
            |-- mvn clean package(做了更多的事)
                组合指令，先执行clean，再执行package，将项目打包，通常应用于发布前
                执行过程: 清理，编译，测试，打包
            |-- mvn clean install
                组合指令，先执行clean，再执行install，将项目打包，通常应用于发布前
                执行过程：清理，编译，测试，打包，安装
    |-- 依赖的作用范围
        |-- compile:编译范围
                指A在编译时依赖B，此范围为默认依赖范围
        |-- provided:
                provided依赖在编译和测试时需要，在运行时不需要
        |-- runtime:
                在运行和测试系统的时候需要，但在编译的时候不需要 
        |-- test:
                在编译和运行时都不需要          




    
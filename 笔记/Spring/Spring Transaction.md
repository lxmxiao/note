# JDBCTemplate
### 依赖包
* Spring 5+1 spring-context
* 数据库驱动： mysql-connector-java 5.1.47
* 连接池： Driod
* spring-jdbc
# Spring Transaction(事务)
```xml
<!--注册事务管理器 使用spring事务必须的组件-->
<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="myds"/>
</bean>
<tx:annotation-driven transaction-manager="txManager"/>
```
配置完使用@Transactional注解
```java
@Transactional//(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,readOnly = true)
public void transfer(int fromId, int destId, int money)
```
```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transactional {}
```
当发生异常时会自动回滚到事务开始的地方

若想自定义回滚位置，需自定义异常





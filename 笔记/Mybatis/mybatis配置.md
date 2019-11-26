# mybatis特点
* 分离sql集中管理   
映射文件 分别管理不同的模块可以解决这个问题
* 定制化sql 更能适应业务复杂度 进行动态sql   
实际上就是：把sql语句暴露给开发者，自己去写。其他的封装起来。去修改非常方便
* MyBatis 可以使用简单的 XML 或注解来配置和映射原生信息
# 使用
## 导包
```xml
<dependencies>
    <!-- mybatis依赖包 -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.2</version>
    </dependency>
    <!-- mysql依赖包 -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.47</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <!-- lombok依赖包 -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.10</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```
## mybatis配置
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <!-- mybatis最基本的配置 -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/homework?useUnicode=true&amp;characterEncoding=utf-8"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <!-- 指向所建的映射文件 -->
        <mapper resource="com\cskaoyan\dao\AccountsMapping.xml"/>
    </mappers>
</configuration>
```
## 映射文件的配置
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 通过namespace+id可以找到唯一的sql语句 -->
<mapper namespace="mynamespace">

    <select id="selectAccountsName" resultType="string">
        select name from accounts where id = #{id}
    </select>

</mapper>
```
* Namespace唯一的
* 同一个映射文件下的id：不能相同；不同的映射文件的id：可以相同
* **Namespace+id：唯一的**
## mybatis的使用
```java
public class SqlTest {

    // 定义全局变量sqlSession和sqlSessionFactory
    // 线程不安全
    SqlSession sqlSession;

    //线程安全
    SqlSessionFactory sqlSessionFactory;

    // 创建sqlSessionFactory对象
    // 因为线程安全问题，sqlSession在使用时创建
    @Before
    public void init() throws IOException {
        // 建造出来builder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // sqlSessionFactory
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
        sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream);
        // 调用sql语句
    }

    // 结束后关闭sqlSession
    @After
    public void after(){
        if (sqlSession != null){
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void selectTest(){
        // 创建sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 查询
        String name = sqlSession.selectOne("mynamespace.selectAccountsName", 2);
        System.out.println(name);
    }
}
```
## 查询和增删改的区别
* 共同点   
都是两个参数：参数1→命名空间+id；参数2→映射文件sql语句执行所需要的参数
* 不同点   
调用的api和标签不同   
   * 查询：不需要提交事务，resultType
   * 增删改：需要提交事务 没有resultType的属性
# sql语句
## 查询
单个查询

java:
```java
String name = sqlSession.selectOne("mynamespace.selectAccountsName", 2);
System.out.println(name);
```
xml:
```xml
<select id="selectAccountsName" resultType="string">
    select name from accounts where id = #{id}
</select>
```
多个查询(是pojo对象不是list)

java:
```java
// 查询后封装到javabean中
List<Accounts> list = sqlSession.selectList("mynamespace.selectAccounts");
System.out.println(list);
```
xml:
```xml
<select id="selectAccounts" resultType="com.cskaoyan.bean.Accounts">
    select * from accounts
</select>
```
## 增
java:
```java
// 可以传对象、map等
Accounts accounts = new Accounts(null, "李四", 3000.0);
// 返回值为改变的行数
sqlSession.insert("mynamespace.insertAccounts", accounts);
```
xml:
```xml
<insert id="insertAccounts">
    insert into accounts values (#{id},#{name},#{balance})
</insert>
```
## 改
java:
```java
Accounts accounts = new Accounts(4, "王五", 3000.0);
int update = sqlSession.update("mynamespace.updateAccountsName", accounts);
```
xml:
```xml
<update id="updateAccountsName">
    update accounts set name = #{name},balance = #{balance} where id = #{id}
</update>
```
## 删
java:
```java
sqlSession.delete("mynamespace.deleteAccounts", 4);
```
xml:
```xml
<delete id="deleteAccounts">
    delete from accounts where id = #{id}
</delete>
```














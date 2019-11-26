# 动态sql(标签)
## where
```xml
<select id="selectUsernameById" resultType="string">
    select username from homework
    <where>
        id = #{id}
    </where>
</select>
```
## if
如果where后直接跟了多余一个关系词（and、or），可以帮我们去掉

如果我们缺少关系词，where不能够帮我们补充

因为xml中无法直接使用<>两个符号，所以用下面两个代替大于小于号
* 大于号：gt
* 小于号：lt
```xml
<select id="selectUserByCondition" resultType="com.cskaoyan.bean.User">
    select id,username,password,age,gender,hobby from j17_user_t
    <where>
        <if test="user.username == 'songge'">
            username = #{user.username}
        </if>
        <!-- 大于15 -->
        <!-- test中也要使用gt -->
        <if test="user.age gt 15">
            age &gt; #{user.age}
        </if>
        <!-- 小于15 -->
        <!-- test中也要使用lt -->
        <if test="user.age lt 15">
            age &lt; #{user.age}
        </if>
    </where>
</select>
```
## trim(省略)
属性：
* Prefix→trim标签里的内容会在trim标签最前面拼接
* Suffix→trim标签里的内容会在trim标签最后面拼接
* PrefixOverrides→如果trim标签最前面包含这个内容，会去掉
* suffixOverrides→如果trim标签最后面包含这个内容，会去掉
```xml
<update id="updateUser">
    update j17_user_t
    <trim prefix="set"  prefixOverrides="aaaa" suffixOverrides=",">
        <!-- aaaa会被删除 -->
        <!-- 如果是aaaaa的话，只会删除前面4个a -->
        aaaa
        <if test="user.username != null">
        username = #{user.username} ,
        </if>
        <if test="user.password != null">
        password = #{user.password},
        </if>
        <if test="user.age != 0">
            age= #{user.age},
        </if>
        <if test="user.gender != null">
            gender=#{user.gender},
        </if>
        <if test="user.hobby != null">
            hobby=#{user.hobby}
        </if>
    </trim>
    where id = #{user.id}
</update>
```
## choose when
```xml
<sql id="baseColumn">
    id,username,password,age,gender,hobby
</sql>
<sql id="whereCondition">
    <where>
        <choose>
            <when test="age gt 15">
                age &gt; #{age}
            </when>
            <otherwise>
                age &lt;= #{age}
            </otherwise>
        </choose>
    </where>
</sql>
```
等同于if else
## sql
```xml
<sql id="baseColumn">
<!-- 把sql语句条件，放入sql片段 -->
<!-- sql的id属于全局变量，可以在其他语句中调用 -->
    id,username,password,age,gender,hobby
</sql>
<sql id="whereCondition">
    <where>
        <choose>
            <when test="age gt 15">
                age &gt; #{age}
            </when>
            <otherwise>
                age &lt;= #{age}
            </otherwise>
        </choose>
    </where>
</sql>
```
## foreach
遍历

属性：
* collection   
参数的类型(如果有注解的话，就是注解的值)
* item   
数组或者集合中的类型(User)
* separator   
每两个遍历之间加入一个值或者字符
* open/close   
在循环最前/最后加入一个值或者字符

```xml
<!--for each标签-->
<!--数组无注解 collection写array-->
<insert id="insertUsers">
    insert into j17_user_t (username,password,age,gender,hobby) values
    <foreach collection="array" item="user" separator=",">
        (#{user.username},#{user.password},#{user.age},#{user.gender},#{user.hobby})
    </foreach>
</insert>
<!--数组有注解 collection写@Param里的值-->
<insert id="insertUsersAnnotation">
    insert into j17_user_t (username,password,age,gender,hobby) values
    <foreach collection="users" item="user" separator=",">
        (#{user.username},#{user.password},#{user.age},#{user.gender},#{user.hobby})
    </foreach>
</insert>
<insert id="insertUsersList">
    insert into j17_user_t (username,password,age,gender,hobby) values
    <foreach collection="list" item="user" separator=",">
        (#{user.username},#{user.password},#{user.age},#{user.gender},#{user.hobby})
    </foreach>
</insert>
<insert id="insertUsersListAnnotation">
    insert into j17_user_t (username,password,age,gender,hobby) values
    <foreach collection="users" item="user" separator=",">
        (#{user.username},#{user.password},#{user.age},#{user.gender},#{user.hobby})
    </foreach>
</insert>
```
## selectKey
用法：
* 在原先sql语句的基础上额外去执行一个sql语句(一般在最前或者最后)
* 最前：插入数据前生成UUID()放到数据的某个字段中或者代替某个字段
* 最后：select LAST_INSERT_ID()

属性：
* order   
在整个sql语句执行之前/之后
* keyColumn   
额外select语句的结果加一个别名
* keyProperty   
输入映射可以使用的值
* resultType   
keyProperty的类型

同一个语句中只能有一个selectKey标签
```xml
<insert id="insertUser">
    <selectKey order="AFTER" keyColumn="idz" keyProperty="user.id" resultType="int">
        select LAST_INSERT_ID() as idz
    </selectKey>
    insert into j17_user_t
    (username,password,
    <if test="user.age gt 15">
        age,
    </if>
    gender,hobby)
    values
    (#{user.username},#{user.password},
    <if test="user.age gt 15">
        #{user.age},
    </if>
    #{user.gender},#{user.hobby})
</insert>
```
# 多表映射
## 一对一
* Javabean：在一个javabean中新增一个另一个javabean类型的成员变量
* 关系表：在一张表中维护另一张表的主键

分次查询：

association标签属性：
* property   
javabean中的成员变量
* column   
第一次查询结果的列，为第二次查询提供参数
* select   
第二次查询语句的id， 第二次查询语句中的#{}里的值来源于column属性
```xml
<!--分次查询-->
<resultMap id="userMap" type="com.cskaoyan.bean.User">
    <result column="id" property="id"/>
    <result column="username" property="username"/>
    <result column="password" property="password"/>
    <result column="age" property="age"/>
    <result column="gender" property="gender"/>
    <result column="hobby" property="hobby"/>
    <!-- association标签代表javabean中存储的另一个javabean对象 -->
    <association property="userDetail" column="id" select="com.cskaoyan.mapper.UserMapper.selectUserDetailByUid"/>
</resultMap>
<select id="selectUserByName" resultMap="userMap">
    select id,username,password,age,gender,hobby from j17_user_t where username = #{name}
</select>
<select id="selectUser" resultMap="userMap">
    select id,username,password,age,gender,hobby from j17_user_t
</select>

<select id="selectUserDetailByUid" resultType="com.cskaoyan.bean.UserDetail">
    select id,mobile,email,height from j17_user_detail_t where uid = #{def}
</select>
```
连接查询：

查询的全部内容(user和user属性中另一个对象的全部内容)用一条语句，显示在同一个结果中
```xml
<!--连接查询-->
<resultMap id="userMapLeft" type="com.cskaoyan.bean.User">
    <result column="id" property="id"/>
    <result column="username" property="username"/>
    <result column="password" property="password"/>
    <result column="age" property="age"/>
    <result column="gender" property="gender"/>
    <result column="hobby" property="hobby"/>
    <association property="userDetail" javaType="com.cskaoyan.bean.UserDetail">
        <result column="udid" property="id"/>
        <result column="mobile" property="mobile"/>
        <result column="email" property="email"/>
        <result column="height" property="height"/>
    </association>
</resultMap>
<select id="selectUserByNameLeft" resultMap="userMapLeft">
    select
    u.id,
    u.username,
    u.password,
    u.age,
    u.gender,
    u.hobby,
    ud.id as udid,
    ud.mobile,
    ud.email,
    ud.height
    from j17_user_t u
    LEFT JOIN j17_user_detail_t ud on u.id = ud.uid
    where u.username = #{name}
</select>
```
## 一对多
* Javabean：在“1”的javabean中新增一个“多”的javabean类型的list
* 关系表：在“多”的表中维护“1”这个表的主键

分次查询：

```xml
<!--分次查询-->
<resultMap id="userMap" type="com.cskaoyan.bean.User">
    <result column="id" property="id"/>
    <result column="username" property="username"/>
    <result column="password" property="password"/>
    <result column="age" property="age"/>
    <result column="gender" property="gender"/>
    <result column="hobby" property="hobby"/>
    <collection property="orderList" column="id" select="com.cskaoyan.mapper.UserMapper.selectOrdersByUid"/>
</resultMap>
<select id="selectUserByName" resultMap="userMap">
    select id,username,password,age,gender,hobby from j17_user_t where username = #{name}
</select>

<select id="selectOrdersByUid" resultType="com.cskaoyan.bean.Order">
    select order_name as orderName,money from j17_order_t where uid = #{id}
</select>
```
连接查询：

属性：
* ofType   
一对多中多的对象
```xml
 <resultMap id="userMapLeft" type="com.cskaoyan.bean.User">
    <result column="id" property="id"/>
    <result column="username" property="username"/>
    <result column="password" property="password"/>
    <result column="age" property="age"/>
    <result column="gender" property="gender"/>
    <result column="hobby" property="hobby"/>
    <collection property="orderList" ofType="com.cskaoyan.bean.Order">
        <result column="oid" property="id"/>
        <result column="order_name" property="orderName"/>
        <result column="money" property="money"/>
    </collection>
</resultMap>
<!--连接查询 1对多-->
<select id="selectUserByNameLeft" resultMap="userMapLeft">
    select
    u.id,
    u.username,
    u.password,
    u.age,
    u.gender,
    u.hobby,
    o.id as oid,
    o.order_name,
    o.money
    from j17_user_t u
    LEFT JOIN j17_order_t o on u.id = o.uid
    where u.username = #{name}
</select>
```
## 多对多
* Javabean：双向的一对多
* 关系表：通过一张关系表（中间表→同时包含两张表的主键）维护多对多的关系

本质做的仍然是一对多

分次+连接查询：
```xml
<resultMap id="studentMap" type="com.cskaoyan.bean.Student">
    <result column="id" property="id"/>
    <result column="student_name" property="studentName"/>
    <collection property="courses" column="id" select="com.cskaoyan.mapper.StudentMapper.selectCourseByStudentId"/>
</resultMap>
<select id="queryStudentByName" resultMap="studentMap">
    select id,student_name from j17_student_t where student_name = #{name};
</select>

<select id="selectCourseByStudentId" resultType="com.cskaoyan.bean.Course">
    select c.id,c.course_name as courseName from j17_course_t c LEFT JOIN j17_relationship_t r on c.id = r.course_id
    where r.student_id = #{id};
</select>
```
纯连接查询：

一次性查询全部的内容，然后进行映射
```xml
<!--课程对学生的一对多
        纯连接查询
-->
<resultMap id="courseMap" type="com.cskaoyan.bean.Course">
    <result column="id" property="id"/>
    <result column="course_name" property="courseName"/>
    <collection property="students" ofType="com.cskaoyan.bean.Student">
        <result column="sid" property="id"/>
        <result column="student_name" property="studentName"/>
    </collection>
</resultMap>
<select id="queryCourseByName" resultMap="courseMap">
    select
    c.id,
    c.course_name,
    s.id as sid,
    s.student_name
    from j17_course_t c
    LEFT JOIN j17_relationship_t r on c.id = r.course_id
    LEFT JOIN j17_student_t s on r.student_id = s.id
    where c.course_name = #{name}
</select>
```
## 小结
* Association：一对一
   * Property：resultMap对应type的成员变量名
   * 分次：
      * Column：查询结果的列名，并且为第二次查询提供参数
      * Select：第二次查询的命名空间+id   #{}里对应的值是来源于column
   * 连接：
      * javaType：右一的类型
* Collection：一对多、多对多（本质是一对多）
   * Property：resultMap对应type的成员变量名
   * 分次：
      * Column：查询结果的列名，并且为第二次查询提供参数
      * Select：第二次查询的命名空间+id   #{}里对应的值是来源于column
   * 连接：
      * ofType：右多的类型




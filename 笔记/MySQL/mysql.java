mysql

    概念和基本操作
        |-- MySQL 是一种关系型数据库,既可以用来存储数据，也可以用来管理数据，mysql的默认端口为3306
            |-- 一些常用操作见MySQL文件夹的增删改查文件

        |-- 数据类型
            |-- 需要注意的MySQL数据类型
                |-- int类型：
                    float 一般存放1000左右
                    double 一般存放百万级
                    decimal 一般为银行使用，内部是字符串形式存储，长度不固定
                |-- String类型：
                    varchar(M) 变长字符串，其中M为给定最大存放字节数，实际存放字节数由实际数据长度决定
                |-- 特殊类：
                    enum 枚举，从给定枚举中选择一个插入 (只能选其中一个，不能选备选项之外的)
                    set 集合，从给定集合中选择一个或者多个插入 (可以选多个，不能选备选项之外的)
                |-- 日期类：  
                //创建表时可以两个都加入，当使用时再决定具体用哪一个
                    datatime 8字节 默认为null 存储和时区无关
                    timestamp 4字节 默认是当前时间 存储和时区有关
                |-- 存放图片、视频等：
                    在数据空中存放地址，地址指向这些数据(也可以是域名)

        |-- 增删改查
            创建
            |-- 注意：
                |-- 当表名、库名和关键字相同时，如果要创建，则在创建表时，表名应添加 "`" 符号
                    创建后，每次使用此表，都必须加入 "`" 符号
            
            更改
            |-- 更改关键字
                |-- change 关键字 可以修改列名，也可以修改列的参数
                |-- modify 关键字 只能修改列的参数

            查询
            |-- 查询的注意事项
                |-- 通配符查询(模糊查询)
                    select * from 表名 where 列名 like 关键字 (关键字不加通配符则搜索不到关键字) 格式： '%...%'(内容包含) '%...'(开头为) '...%'(结尾为)
                    '%' 省略前或者后的内容(多个字符)
                    '_' 省略前或者后的其中一个字符
                |-- 分页查询
                    LIMIT number OFFSET offset (number：每页显示的数据，offset：第几页)
                |-- 聚合函数
                //计算的单位都是列
                    count(*) 计算表中总行数
                    count(column_name) 计算指定列中的总行数，不会把null计入
                    sum() 返回指定列的和
                    avg() 返回指定列的平均值
                    max() 返回指定列的最大值
                    min() 返回指定列的最小值
                |-- 分组
                    |-- group by 之后，其他字段的信息会合并，所以select时会出现信息显示不全
                        可以使用 group_concat(列名) 显示列中被合并的信息
                        |-- where 先筛选之后分组
                            having 分组之后再筛选

                多表查询
                |-- 连接查询
                    |-- 内连接
                        显示两表中所有符合表达式的数据(常用)
                    |-- 左外连接
                        显示两表中所有符合表达式的数据，同时会显示左表所有的数据
                    |-- 右外连接
                        显示两表中所有符合表达式的数据，同时会显示右表所有的数据
                |-- 联合查询
                    |-- 合并两条查询语句的查询结果，去掉其中重复数据行，返回结果
        
        |-- 主键，外键
            |-- 主键
                |-- 主键设置后，主键不能为null  (主键最好设置为int类型)
                    当主键设置自增长后，可以插入null值
                |-- 主键 primary key           (没有设置自增长时，不可以插入null值)
                |-- 自增长 auto_increment
                |-- 删除主键
                    删除主键时，如果主键设置了auto_increment，需要先删除auto_increment，再删除主键
            |-- 唯一性约束
                |-- unique 可以插入null，也可以有多个null
            |-- 外键
                |-- 用来约束两个表之间的关系
                    一对多
                    多对多
                |-- 注意：
                    删除主表中的一行数据时，必须先删除副表中与之关联的数据，再删除主表的数据

        |-- 数据库三大范式
            |-- a.每列保持原子性
                |-- 列中为最小字段，即不可再分性     例如：收货地址(省、市、县、街道)
            |-- b.记录的唯一性
                |-- 其他非主键字段只与主键存在关系，在同一个表中不存在子关系
            |-- c.字段不要冗余
                |-- 表中数据不要存在传递依赖关系    
                    例如：学号、姓名、年龄、学院名称、学院电话
                    前面为学生信息，后面为学校信息，可以把这两个信息分开存放在两个不同的表中，然后用一列数据把两表联系起来

        |-- 数据库的备份与恢复
            |-- 备份
                |-- 命令：cmd命令下
                        mysqldump -u root -p 数据库名 > 需要备份到的文件名
            |-- 恢复
                |-- 命令：mysql命令下
                        a. create database 库名
                        b. use 库名
                        c. source 备份文件(直接拖进来就行)

    JDBC
        |-- 步骤
            |-- 导入MySQL的jar包
                如果选择的jar包level是project级别，则在其他的module中，不需要再次重复执行该操作，直接在open module setting中，选择dependencies中，选择project的依赖包
            |-- 程序优化
                实现的很多重复步骤可以创建一个工具类
        |-- JDBC 中的重要对象
            |-- DriverManager
            |-- Connection
                |-- 方法
                    |-- createStatement()：创建向数据库发送sql的statement对象
                    |-- prepareStatement(sql)：创建向数据库发送预编译sql的PrepareSatement对象
            |-- Statement
                |-- 方法
                    |-- executeQuery(String sql)：用于向数据发送查询语句
                    |-- executeUpdate(String sql)：用于向数据库发送insert、update或delete语句
                    |-- execute(String sql)：用于向数据库发送任意sql语句
            |-- ResultSet
                |-- 对sql查询结果的封装。内部有一个游标，初始时，指向第一行数据之前，在查询数据之前必须先调用一下next方法
                |-- 提供各类get方法可以用来获取相应的数据
        |-- 批处理
            |-- Statement
                |-- Statement.executeBatch(sql)         把 sql 语句放入一个容器中
                |-- Statement.clearBatch()              清空容器
                |-- perpareStatement.executeBatch()     把 perpareStatement 语句放入一个容器中
        |-- Statement 和 PerpareStatement 批处理的区别
            |-- Statement 可以执行多条sql类型不同的sql语句，而perpare无法做到，因为它需要将sql语句进行一次预编译
            |-- perpare 性能会更高，预编译比较耗时，Perpare 只进行了一次编译，而 statement 要编译多次
        |-- 默认情况下 MySQL 的批处理仍然是一条一条执行，需要在url后面添加
                rewriteBatchedStatements = true 参数
    
    事务
        |-- 特性
            |-- 原子性  指事务是一个不可分割的单位，事务中的操作要么都发生，要么都不发生
            |-- 一致性  事务必须使数据库从一个一致性状态变换到另一个一致性状态  例如：两账户转账，不管怎么转，两账户的和不变
            |-- 隔离性(主要)    是多个用户并发访问数据库时，数据库为每一个用户开启的事务，不能被其它事务的操作数据所干扰，多个并发事务之间要相互隔离
            |-- 持久性  指事务一旦被提交，它对数据库的改变就是永久性的
        |-- 隔离不做好，会发生的问题
            |-- a.脏读          提交前可被查询，如果回退，另一方查询结果不一致
            |-- b.不可重复读    提交前不可被查询，但另一方多次查询，可能会出现结果不一致
            |-- c.虚读(幻读)    插入数据后，另一方查询多次，可能会出现数据多出的现象
        |-- 四种隔离等级
            |-- Serializable            可避免脏读、不可重复读、虚读
            |-- Repeatable read         可避免脏读、不可重复读
            |-- Read committed          可避免脏读
            |-- Read uncommitted        最低等级，无法保证
        //重点
            |-- MySQL 的 Repeatable read 不允许虚读，而且 MySQL 的默认隔离等级为 Repeatable read
        |-- 在 JDBC 中使用事务
            |-- connection.setAutoCommit(false)              //手动提交事务
            |-- connection.rollback()                        //回退     放在catch语句中
            |-- SarePoint sp = connection.setSarePoint
                connection.rollback(sp)                      //回退到指定位置
            |-- connection.commit()                          //回退到指定位置后需提交

    数据库连接池
        |-- 出现的原因
            |-- 因为每次 JDBC 的连接是非常消耗性能的，所以出现了数据库连接池，在池中预先放一些数据库连接，当用户访问时，直接在连接池中拿出连接，而不是新建一个连接
        |-- 自己实现连接池
            |-- 首先为了规范连接池，必须实现 DataSource 接口，但 DataSource 接口中并没有关闭回收连接池的方法
                这时候就要重新实现 Connection 类，在自己实现的 Connection 类中传入一个 Connection 的实现类，然后重写close方法，回收连接池
        |-- 第三方开源的数据库连接池
            |-- DBCP    //常用
            |-- C3P0    //好用，但逐渐被DBCP替代
            |-- Druid
        |-- 创建第三方连接池
            |-- dreid
                |-- 文件获取
                    |-- a.file获取  (Module根目录)
                        properties.load(new FileInputStream(new File("druid.properties")));
                    |-- b.类加载器  (src目录下)
                        InputStream in = DruidUtils.class.getClassLoader().getResourceAsstream("druid.properties");
                        properties.road(in);
                |-- 获取连接池对象
                    |-- DataSource dataSource = new DruidDataSourceFactory().createDataSource(properties);

    |-- DBUtils
        |-- 构造方法
            |-- QueryRunner()
                无参构造函数，后续调用方法时需传入 Connection 对象
            |-- QueryRunner(DataSource ds)
                需要获取连接池对象,后续调用方法无需传入 Connection 对象
        |-- Method
            //查询
            |-- Query 
                |-- 主要的返回方法
                    |-- BeanHandler: 将结果集中的第一行数据封装到一个对应的 JavaBean(封装的类) 实例中
                    |-- BeanListHandler: 将结果集中的每一行数据都封装到一个对应的 JavaBean(封装的类) 实例中，并存放到 List 中
                    |-- ColumnListHandler: 将结果集中某一列的数据存放到 List 中
                    |-- ScarlarHandler: 将单个值封装，可以用来统计聚合函数方法的返回值
            //修改
            |-- Update
                |-- 具体方法和传入参数和 Query 相同
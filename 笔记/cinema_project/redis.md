# redis 特点

1. redis是单线程的，省去了很多上下文切换线程的时间
2. 基于内存
3. 可持久化
4. 日志型
5. key-value数据库

每次重启Redis服务时，数据可能会丢失（取决于持久化策略）

# redis 介绍

NoSQL 非关系型数据库

可基于内存 , 可持久化的日志型、Key-Value数据库 

去取数据的时候，指定key即可。不能用sql语句。

redis 默认有16个库（可以配置）

Redis是一个开源的内存数据库，它以键值对的形式存储数据。由于数据存储在内存中，因此Redis的速度很快，但是每次重启Redis服务时，其中的数据也可能会丢失，因此，Redis也提供了持久化存储机制，将数据以某种形式保存在文件中，每次重启时，可以自动从文件加载数据到内存当中.

# redis 存储策略

## RDB

Redis DataBase

默认存储方式 ，保存内存快照，fork一个子进程去存储，每隔一段时间把所有东西保存在文件中

默认会存储到dump

优点：

- 数据全
- 数据存放整齐

缺点：

- 占用性能

>  jdk提供的JMAP可以对JVM快照，分析JVM内存溢出等信息

## AOF

Apend-Only File

文件追加，保存变化

以日志形式记录Server处理的每一个增删改

# redis 支持的数据类型

## 字符串（value的类型）

跟之前的 map非常类似

| 命令          | 功能                              |
| :------------ | :-------------------------------- |
| SET key value | 设值                              |
| GET key       | 获得key的value                    |
| INCR          | 可以对应key的数值加上一个步长     |
| INCRBY        | 给数值加上一个步长                |
| SETEX         | expire过期                        |
| SETNX         | not exit key 不存在的时候再去赋值 |

## List

| 部分命令 | 行为                                                        |
| :------- | ----------------------------------------------------------- |
| LPUSH    | 将给定值推入列表的左端                                      |
| RPUSH    | 将给定值推入列表的右端                                      |
| LRANGE   | 获取列表在给定范围上的所有值                                |
| LINDEX   | 获取列表在给定位置上的单个元素                              |
| LPOP     | 从列表的左端弹出一个值，并返回被弹出的值                    |
| RPOP     | 从列表的右端弹出一个值，并返回被弹出的值                    |
| LLEN     | 返回list列表的长度                                          |
| LINSERT  | 插入的位置是按照index的顺序，before的话需要注意index的值    |
| LREM     | 删除list里的指定的前几个（指定value）的元素                 |
| LSET     | 设定指定的元素的值，输入的index是从0开始，显示的标号从1开始 |

## Hash

| 命令    | 行为                                                         |
| ------- | ------------------------------------------------------------ |
| HSET    | 在散列里面关联起给定的键值对                                 |
| HGET    | 获取指定散列的值                                             |
| HGETALL | 获取散列包含的所有键值对                                     |
| HDEL    | 如果给定键存在于散列里面，那么移除这个键                     |
| HEXISTS | 表里的key是否存在                                            |
| HKEYS   | 获取散列所有的键                                             |
| HLEN    | 有几行数据                                                   |
| HVALS   | 获取散列所有的值                                             |
| HMGET   | 给一个hash表多个key，返回多个值                              |
| HMSET   | 给一个hash表多个key设定值                                    |
| HSETNX  | hash表的key不存在会创建一个key，然后设值，如果存在，不会改变该key的值 |

## Set （无序集合）

| 部分命令              | 行为                                         |
| --------------------- | -------------------------------------------- |
| SADD                  | 将给定元素添加到集合                         |
| SMEMBERS              | 返回集合包含的所有元素                       |
| SISMEMBER             | 检查给定元素是否存在于集合中                 |
| SREM                  | 如果给定的元素存在于集合中，那么移除这个元素 |
| SCARD                 | 集合的长度                                   |
| SPOP                  | 弹出并从集合删除                             |
| SRANDMEMBER           | 随机取出n个元素（不删除）                    |
| SINTER                | 求出两个集合相交的部分                       |
| SINTERSTORE           | 把相交的集合保存起来                         |
| SUNION                | 求两个集合的并集                             |
| SUNIONSTORE           | 把两个集合的并集保存起来                     |
| SDIFF 集合1 集合2     | 集合1减去集合1和集合2的交集（补集）          |
| SDIFFSTORE            | 把补集保存起来                               |
| SMOVE 集合1 集合2 值1 | 把集合1的值1转移到集合2                      |
| SREM                  | 删除集合中指定的值                           |

## SortSet（有序的集合）

用于存储键值对

> 有序集合的键被称为成员（member），每个成员都是各不相同的
>
> 有序集合的值则被称为分值（score），分值必须为浮点数
>
> 有序集合是Redis里唯一一个既可以根据成员访问元素，又可以根据分值以及分值的排列顺序来访问元素的结构

| 命令                   | 行为                                                       |
| ---------------------- | ---------------------------------------------------------- |
| ZADD                   | 将一个带有给定分值的成员添加到有序集合里面                 |
| ZRANGE                 | 根据元素在有序排列中所处的位置，从有序集合里面获取多个元素 |
| ZRANGEBYSCORE          | 获取有序集合在给定分值范围内的所有元素                     |
| ZREVRANGEBYSCORE       | 逆序输出有序集合在给定分值范围内的所有元素                 |
| ZREM                   | 如果给定成员存在于有序集合，那么移除这个成员               |
| ZCARD                  | 有序集合的长度                                             |
| ZSCORE                 | 有序集合里指定成员的分数                                   |
| ZCOUNT（闭区间）       | 查询分数区间的成员数量                                     |
| ZINCRBY 集合 分数 成员 | 给指定成员增加分数                                         |
| ZRANK                  | 查询成员的分数排名（排名由0开始，分数升序）                |
| ZREVRANK               | 查询成员的分数排名（排名由0开始，分数降序）                |
| ZREM                   | 删除指定成员                                               |
| ZREMRANGEBYRANK        | 删除指定排名的成员                                         |
| ZREMRANGEBYSCORE       | 删除指定分数区间的成员                                     |

## 删库

flushall	删库

# 单点登录

设置序列化

```java
@Configuration
public class RedisConfig {

    @Bean
    public Jedis jedis(){
        Jedis jedis = new Jedis("0.0.0.0", 6379);
        return jedis;
    }
	
    // 序列化设置，不设置的话取出来的是LinkedHashMap
    // 设置以后，Redis中存放对象中存放对象的时候会带上全限定类名，取出的时候可以反射生成对象
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}

```
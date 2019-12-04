# 安装RocketMQ

## 下载

下载地址：官网地址：http://rocketmq.apache.org/release_notes/release-notes-4.4.0/

安装后修改 rocketMQ 的环境变量，同时因为 rocketMQ 是基于 Java 开发的，所以需要配置相应的 jdk 环境变量才能运行

## 启动

修改bin目录下的 `NameServer `和 `Broker` 配置

编辑 `runbroker.cmd` 文件，修改如下：

![image-20191204161140202](RocketMQ%E4%BD%BF%E7%94%A8.assets/image-20191204161140202.png)

* -Xms：设置 JVM 促使内存
* -Xmx：设置 JVM 最大可用内存
* -Xmn：设置 JVM 内存中年轻代大小

编辑runserver.cmd文件，修改如上图一样

然后开始启动程序：

1. 启动注册中心 `nameserver `，默认启动在 9876 端口，在 cmd 窗口下，执行命令：`start mqnamesrv.cmd`

2. 启动 `rocketMQ `服务，也就是 `broker`

   执行 `start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true`

   `autoCreateTopicEnable=true`这个设置表示开启自动创建topic功能

启动后，可以输入` jps -v` 命令查看状态

## 导包

4.4.0版本

```xml
<!-- https://mvnrepository.com/artifact/org.apache.rocketmq/rocketmq-client -->
<dependency>
    <groupId>org.apache.rocketmq</groupId>
    <artifactId>rocketmq-client</artifactId>
    <version>4.4.0</version>
</dependency>
```

## Consumer

```java
public class Consumer {

    public static void main(String[] args) throws MQClientException {

        // 获得接受者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_group");

        // 设置NameServer(注册中心)ip和端口号
        consumer.setNamesrvAddr("localhost:9876");

        // 设置匹配的topic(主题)，及它所可以匹配的标签
        consumer.subscribe("17thTest","*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                //获取提供者传过来的消息
                MessageExt messageExt = msgs.get(0);
                byte[] body = messageExt.getBody();

                String bodyStr = new String(body);

//                System.out.println(bodyStr);
                HashMap hashMap = JSON.parseObject(bodyStr, HashMap.class);

                Integer id = (Integer) hashMap.get("id");

                System.out.println("从消息中取到的id是："+id);


                //有重试机制 16次

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

    }
}
```

## Prodecer

```java
public class Producer {

    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {

        // 获得提供者组
        DefaultMQProducer producer = new DefaultMQProducer("producer_group");
        
        // 设置注册中心ip和端口号
        producer.setNamesrvAddr("localhost:9876");

        // 开启提供者
        producer.start();

//        String body = "今天天气真好啊！！";
        // 设置需要传递的消息
        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("id",1);
        String body = JSON.toJSONString(hashMap);

        // topic，消息，其他参数(其中可以设置标签)
        Message message = new Message("17thTest", body.getBytes(Charset.forName("utf-8")));

        SendResult sendResult = producer.send(message);

        System.out.println(JSON.toJSONString(sendResult));

    }
}
```

## @PostConstruct

bean被初始化时会先执行这个注解中的方法


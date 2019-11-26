Servlet
    |-- Servlet的执行过程
        |-- 1、域名解析
            2、浏览器生产http请求报文
            3、向下经过tcp层，tcp将http报文拆分为若干段
            4、向下到ip层，给每个片段加上ip头部
            5、一边中转，一边传输
            6、到达目标及其，经过tcp层，然后将请求报文重新组装
            7、接着到达应用层，被正在监听着某个端口号的服务器软件接收到
            8、解析请求报文，获取所要请求的资源名称
            9、服务器去作出对应的相应
            10、构建响应报文，然后将数据填充到响应体中
    |-- Servlet开发
        |-- 开发方式
            |-- 继承HttpServlet
                继承HttpServlet后，每次执行会自动执行 HttpServlet 中的 service 方法，HttpServlet 中的 service 方法是继承自父类 GenericServlet 中的 service 方法，不做事情
            |-- 通过注解的方式创建servlet
                |-- @WebServlet("/xxx")
    |-- IDEA的最终项目部署目录和开发目录之间的区别
        |-- 开发过程中，我们见到的目录称之为开发目录
            最终根据应用名.xml文件中配置的docBase目录，我们称之为项目的部署目录
    |-- Servlet的生命周期
        |-- service在第一次访问时被创建，由Tomcat创建
            init()函数会在页面被访问时，初始化容器时调用，只执行一次
            service()函数在访问servlet时候会被调用，可执行多次
            destroy()在结束程序时被调用，也就是关闭容器时，只执行一次
        |-- 可以通过一个参数让service在项目加载的时候自动创建
            Load-on-startup 可以更改servlet的生命周期的创建阶段 设置一个非负数，表示随着应用的启动而实例化
    |-- Url的映射问题
        |-- Url-pattern的写法：
            固定只有两种写法
            以 "/" 或者以 "/*"开头
        |-- 两个重要的问题：
            多个servlet不可以配置同一个url
            一个servlet可以配置多个url
    |-- Url冲突匹配问题
        |-- "/" 开头的url-pattern优先级高于*.后缀的优先级
            都是"/"开头的url-pattern，满足精准匹配，匹配程度越高，执行谁

                这里面讨论几个比较特殊的url-pattern，一个是"/*",还有一个是"/"（"/*"和"/"同时存在的情况下）
                当"/*"存在时，访问html页面或者jsp页面均无法访问到
                当把"/*"注释之后，访问html或者图片均会被"/"拦截，jsp页面可以正常显示
                "/*"的优先级高于"*."jsp高于/

                存在"/*"和"/"的情况下：
                访问jsp和html页面，均无法访问到，都被"/*"拦截掉
                当把"/*"去掉之后，jsp可以访问到（jsp本质上也是servlet，它有一个url-pattern）
    |-- ServletContext对象
        |-- 共享数据
            SetAttribute   getAttribute	  removeAttribute
            服务器会为每一个工程创建一个对象，这个对象就是ServletContext对象。这个对象全局唯一，而且工程内部的所有servlet都共享这个对象。所以叫全局应用程序共享对象。
    |-- 获取EE项目的文件路径
        |-- JavaEE中的main方法入口在bin目录下的bootstrap.jar文件中
			getRealPath 获取应用根目录的绝对路径

1. 客户端发送请求给dispatcherServlet进行处理   
DispatcherServlet启动时，它会创建Spring应用上下文并加载配置文件或配置类中所声明的bean或者自动扫描的bean

web.xml  url-pattern=/拦截
   * 静态文件会被静态资源处理器处理
      1. default Servlet
      2. default Servlet handler
      3. mvc:resources mapping=/abc/**   
location=webroot、classpath、file
2. 经过HandlerMapping(ioc/di 控制反转和依赖注入)(整体经过Spring容器的生命周期)
   * 如果存在拦截器   
先执行拦截器，后交给RequestMappingHandlerMapping处理，处理完后交由拦截器，然后返回dispatcherServlet
   * 如果不存在拦截器   
直接交由RequestMappingHandlerMapping处理
3. 由@RequestMapping接收参数(包括参数、头信息)
4. 经过转换器转换HandlerAdapter，交由Handler处理，最后返回数据给dispatcherServlet
   * 返回的数据可以是String、ModelAndView、void和json
5. 转给viewResolves处理
   * Freemarker
   * InternalResourceViewResolver
6. 过程中如果出现异常，会由异常管理器捕捉(HandlerExceptionResovler)进行处理

* `DispatcherServlet`前端控制器：接收request，进行response
* `HandlerMapping`处理器映射器：根据url查找Handler。（可以通过xml配置方式，注解方式）
* `HandlerAdapter`处理器适配器：根据特定规则去执行Handler，编写Handler时需要按照HandlerAdapter的要求去编写。
* `Handler`处理器（后端控制器）：需要程序员去编写，常用注解开发方式。
   * `Handler`处理器执行后结果是`ModelAndView`，具体开发时`Handler`返回方法值类型包括：`ModelAndView`、`String`（逻辑视图名）、`void`（通过在Handler形参中添加request和 response，类似原始 servlet开发方式，注意：可以通过指定response响应的结果类型实现json数据输出）
* `View Resolver`视图解析器：根据逻辑视图名生成真正的视图（在springmvc中使用View对象表示）
* `View`视图：jsp页面，仅是数据展示，没有业务逻辑。

Filter 过滤器
    |-- 概念
        过滤器由Servlet容器调用，用来拦截以及处理请求和响应。过滤器本身并不能生成请求和响应对象，但是可以对请求和响应对象进行检查和修改
    |-- 原理
        过滤器介于客户端与Servlet/JSP等相关的资源之间，对于与过滤器关联的Servlet来说，过滤器可以在Servlet被调用之前检查并且修改request对象。在Servlet调用之后检查并修改response对象
    |-- 方法
        |-- void init(FilterConfig config)
            用于初始化过滤器
        |-- void doFilter(ServletRequst request, ServletResponse response, FilterChain chain)
            此方法是Filter接口的核心方法，用于对请求对象和响应对象进行检查和处理。此方法包括三个输入参数。其中，ServletRequest对象为请求对象，包括表单数据、Cookie以及HTTP请求头等信息；ServletResponse对象为响应对象，用于响应使用ServletRequest对象访问的信息；FilterChain用来调用过滤器链中的下一个资源，即将ServletRequest对象以及ServletResponse对象传递给下一个过滤器或者是其它的Servlet/JSP等资源
        |-- void destroy()
            此方法用于销毁过滤器
    |-- 过滤器的使用步骤
        1．创建一个实现Filter接口的Java类；
        2．实现init( )方法，如有必要，读取过滤器的初始化参数；
        3．实现doFilter( )方法，完成对ServletRequest对象以及ServletResponse对象的检查和处理；
        4．在doFilter( )方法中调用FilterChain接口对象chain的doFilter( )方法，以便将过滤器传递给后续的过滤器或资源。
        5．在web.xml中注册过滤器，设置参数以及过滤器要过滤的资源。 
    |-- 多个Fileter
        |-- 多个Filter对同一个资源进行了拦截，那么当我们在开始的Filter中执行chain.doFilter(request,response)时，是访问下一下Filter,直到最后一个Filter执行时，它后面没有了Filter,才会访问web资源
        |-- 关于多个FIlter的访问顺序问题
            它们的执行顺序取决于<filter-mapping>在web.xml文件中配置的先后顺序
            注解配置，按照类名的ASCII码表顺序
    |-- Filter生命周期
        |-- 创建
            当服务器启动，会创建Filter对象，并调用init方法，只调用一次.
        |-- 使用
            当访问资源时，路径与Filter的拦截路径匹配，会执行Filter中的doFilter方法，这个方法是真正拦截操作的方法.
        |-- 销毁
            当服务器关闭时，会调用Filter的destroy方法来进行销毁操作.






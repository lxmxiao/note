Session
    |-- 服务端技术，给每个用户的浏览器使用，用以存放一些数据信息
        |-- 首先，浏览器第一次访问服务器时，服务器会给当前的浏览器新建一个session对象，session对象有一个ID，也就是JSESSIONID，之后，服务器会将ID以cookie的形式发送回浏览器端
            浏览器端保存该cookie，再次访问的时候，带上该cookie。服务器接收到该cookie，提取出里面的JSESSIONID的值，然后找到对象session对象的内容
    |-- Session 的使用
        |-- Session 的创建
            |-- Request.getSession()/getSession(boolean)
                getSession()方法返回一个session，如果当前没有session，则创建一个
                getSession（create）,如果create是true的话，有则返回一个session对象，没有则创建一个。如果为false的情形，有则返回一个，没有则不会创建，返回null
        |-- 访问
            |-- 第一次访问
                会发送一个响应头，里面是JSESSIONID=value值
            |-- 再次访问
                已记住信息
        |-- 关闭浏览器，重新再访问，会创建新的session吗？
            会重新创建一个新的session对象
            |-- 传递
                session的传递依赖于cookie，浏览器每次访问都会带上 cookie:JSESSIONID=...... 请求头，服务器判定该ID是否有效，如果有效，则找到id对应的session对象，如果无效，则服务器认为是一个全新的浏览器
            |-- 关闭浏览器后，如果想要再次开启浏览器使用之前的数据
                因为session对象依赖于cookie中的JSESSIONID，所以要使用上次浏览器的session对象只需要保留cookie就行
    |-- 整个session执行过程
            第一次请求，没有带上cookie，则服务器会给当前的浏览器新建一个session对象，同时把session的ID值，也就是JSESSIONID以响应头的形式发送给浏览器set-Cookie。
            第二次请求，浏览器会带啥cookie，服务器在拿到cookie之后，解析出里面的JSESSIONID，找到相应的session，因为session已经存在，则不会再新建session了。
            为什么关闭浏览器之后，服务器又会重新新建一个session对象？？？？
            因为session的传递依赖于cookie，cookie默认的存在形式是存活在浏览器内存中，所以当关闭浏览器再次重新打开，则cookie失效，等价于第一次请求。
    |-- Session的用法
        |-- HttpSession session = request.getSession();
        |-- String name = (String) request.getSession("name");
    |-- Session的生命周期
        |-- 创建
            Session的生命周期，request.getSession()方法调用，且当前浏览器不存在session的时候，会创建
        |-- 对象使用时(或者销毁时)
            关闭服务器，session对象会销毁，重新打开服务器，会重新创建一个session对象，但是session中保存的内容不会丢失(序列化、反序列化)
            对于session中的数据而言，session数据的丢失不是服务器的关闭，而是session的有效期（默认情况下是30分钟有效期，30分钟内无人访问该session，则session失效）和主动调用session.invalidate()
            关闭服务器，session对象会重新创建，但是session中的内容不会丢失
        |-- 对象销毁
            服务器关闭、应用卸载
    |-- Session 域
        |-- setAttribute/ getAttribute /removeAttribute
    |-- 访问index.jsp时session会创建，为什么？
        jsp九大对象中有session，所以在访问jsp时会自动创建session对象
        访问html和servlet不会

|-- JSP中的九大对象(默认自动创建)
    |-- request 对象
    |-- response 对象
    |-- session 对象
    |-- application 对象    (context)
    |-- out 对象
    |-- pageContext 对象
    |-- config 对象
    |-- page 对象
    |-- exception 对象

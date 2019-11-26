Response对象
    |-- response 就是对响应报文的封装
        可以操纵 response对象来控制整个响应报文的信息
        Response.setStatus 设置相应的状态码
        Response.setHeader(name,value)通用的一个形式   设置响应头
        Response.getWriter() /getOutputStream() 设置响应体
    |-- 中文乱码问题
        //更改服务器端编码
        request.setCharacterEncoding("utf-8");
        //更改服务端的数据采用utf-8编码
        //第一种
            response.setContentType("text/html;charset=utf-8");
        //第二种(本质上是一样的)
            response.setHeader("content-type","text/html;charset=utf-8");
        //第三种
            response.setCharacterEncoding("utf-8")+response.getWriter().print(html的meta标签头 meta charset=utf-8)以响应头的形式告知浏览器采用何种编码
        //第四种
            //直接打印前段代码(这也是jsp的原理)
    |-- 字节流传输文件
        String realPath = getServletContext().getRealPath("资源名");
        File file = new File(realPath);
        ServletOutputStream out = response.getOutputStream();
		//对于一些图片之类的文件，不能把Content-Type更改为"text/html"
        //之后直接利用字节流传输就行
    |-- 字节流输出中文
        |-- response.setContentType("text/html;charset=utf-8");
			ServletOutputStream out = response.getOutputStream();
			out.write("你好".getBytes("utf-8"));
		|-- OutputStream和getWriter冲突，这两个只能用一个
    |-- 定时刷新(主要用于页面跳转)
        //第一种(每隔2s反复刷新当前页面)
            response.setHeader("refresh","2");
        //第二种(等待2s后跳转到指定页面)
            response.setHeader("refresh","2;url=http://www.baidu.com");
    |-- 重定向(转到指定页面)
        //第一种
            response.sendRedirect(response.getContextPath() + "/资源名");
        //第二种
            response.setStatue(302);
            response.setHeader("Location",response.getContextPath() + "/资源名");
    |-- 重定向、定时刷新、转发和包含
        |-- 该怎么选择
            1.如果需要多个组件之间共享数据，转发
            2.如果想要页面跳转有更好的一个交互性，可以选择定时刷新
            3.如果要跳转至外部链接(当前应用外的链接)，一定要选择重定向或者定时刷新
        |-- 三者之间的区别
            1.执行主体：转发是服务器；重定向、定时刷新是浏览器
            2.什么对象介导：转发包含是request对象；重定向、定时刷新是response对象
            3.能否共享request域：转发包含可以共享；重定向、定时刷新不可以(请求2次)
            4.地址栏url是否发生变化：转发包含不变化；重定向、定时刷新变化
            5.发送请求的次数：转发包含1次；重定向、定时刷新2次
            6.能否访问外部链接：转发包含不可以；重定向、定时刷新无限制
        |-- 重定向和定时刷新的区别
            重定向状态码：302/307
            定时刷新状态码：200
    |-- 文件下载
        |-- 浏览器对于自身可以打开的文件执行的是打开操作，对于无法打开的文件，比如exe文件，则执行下载操作
    |-- 各路径地址写法总结
        |-- Form表单地址路径action，a标签href，img的src属性，css，js，重定向，定时刷新 凡是浏览器作为执行主体的，/开头的路径的写法都是/应用名/资源名
        |-- GetRealPth(path),getRequestDispatcher（path）,执行主体是服务器，那也就是/开头的写法，/资源名
        |-- getRequestDispatcher(path) /资源名，不需要加应用名
        |-- Response.sendRedirect(url);
            Refresh  url=   Location = 
            /应用名/资源名
            执行主体，浏览器，就加应用名
    |-- WEB-INF目录限制
        |-- 限制的是浏览器，无法访问该目录下的内容，但是对于服务器而言，它是完全开放的


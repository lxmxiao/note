Cookie
    |-- 会话
        |-- 指一次交谈，对于WEB而言，打开www.taobao.com开始，到最后浏览结束，关闭页面，这整个过程，称之为会话
            服务端通过http请求并不能区分多个请求是同一个用户发出还是不同用户发出
            引入会话技术，用来保存上下文信息
    |-- Cookie
        |-- Cookie是客户端技术，服务器产生的cookie通过set-Cookie响应头发送回浏览器，浏览器再次访问该服务器时，会带上该cookie信息。这样就可以解保存用户自身数据
    |-- 特点
        |-- 数据存储量比较小
        |-- 存储位置在浏览器，会有一定的风险
        |-- 一般用cookie存储一些不怎么敏感的数据
    |-- Cookie 的使用
        |-- Cookie cookie = new Cookie("name","zhangsan");
            response.addCookie(cookie);
            //执行后会将创建好的cookie发送回浏览器端保存
    |-- Cookie 的存活时间
        |-- Cookie默认情况下，当你关闭浏览器，则保存的cookie失效
            默认是保存在浏览器的内存中，浏览器关闭，则cookie消失
        |-- 想要让cookie长久保存
            Cookie.setMaxAge(),单位是秒(int)
            正数表示cookie将在多少秒之后失效。
            负数表示cookie存在于浏览器内存中，浏览器关闭，则失效。没有设置的情况下，默认就是该情形
            0表示该cookie将被删除
            |-- 设置MaxAge为正数
                关闭浏览器再次打开该服务器，cookie仍然存在，时间结束后，cookie无效
            |-- 设置MaxAge为0
                删除cookie时，如果cookie设置了相应的path，则还应该保持path和新建cookie时的path一致，否则无法删除cookie
                此时cookie无法被删除，因为删除cookie必须要保证路径一致才可以
            |-- 不同浏览器之间共享
                不同浏览器之间不能否共享cookie
    |-- 设置cookie的path
        |-- 不设置
            如果不设置，则默认访问当前域名下所有资源时，都会带上该cookie
        |-- 设置
            设置path后，只有访问path页面才会有cookie
            删除cookie也需要带上path
            cookie的path路径为 "/应用名/资源名"(浏览器端)


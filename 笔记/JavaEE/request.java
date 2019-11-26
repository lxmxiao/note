ServletRequest重点
1.可以利用request对象尝试去获取请求报文中的所有内容
2.获取请求参数: getParameter系列API 4个，利用反射将请求的参数封装到bean中
3.请求参数中中文乱码问题: post可以通过request.setCharacterEncoding(charset) get方式，如果前段采用UTF-8，则没有乱码问题
4.路径的写法: a.全路径：http//localhost/app/servlet b./应用名开始的写法: 执行主体 3.资源名 两个路径写出来，然后通过对比，如何拼接处目标路径
5.转发包含：
	如果某个功能需要多个组件共同参与来完成，则需要转发包含引入其他组件
	转发包含的区别：
		执行过后主动权是否在源组件中：转发过后，主动权交给目标组件;包含后，主动权仍然在源组件，具体代码上面的体现就是：都是对于源组件而言的，转发是留头(响应头)不留体(响应体);包含是留头也留体
6.request域：
	request对象中存在一个map，可以在map立存取数据，得到的事同一个request对象，转发包含是同一个request对象
	注意和context域的区别：大小上面的区别：context域最大，代表了当前应用，凡是应用内的所有组件均可以在里面存取数据，request域仅限于一次请求内有效，转发包含源组件和目标组件可以来存取数据
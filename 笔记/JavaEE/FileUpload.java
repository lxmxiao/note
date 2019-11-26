FileUpload
    |-- 思路：
        上传的文件会在请求报文中，请求报文被tomcat封装到request对象中
    |-- 文件上传遇到的问题：
        |-- 仅上传文件名，不上传文件内容
            需在form表单中添加 enctype="multipart/form-data" 属性
        |-- 写代码处理上传，图片无法打开，图片损坏
            在上传过程中，表单数据和上传文件会一起写入文件中，后面又没有进行分割
        |-- 添加 "multipart/form-data" 属性后，request.getParameter API 不再适用
            原因是数据结构发生了改变，所以无法再获取到参数
            添加 "multipart/form-data" 属性后请求正文中会多出很多分隔符
        |-- 主要问题：
            必须要添加multipart/form-data,不添加则不能上传文件
            添加之后，必须手动来处理普通form表单上数据和文件数据
    |-- Commons-FileUpload
        |-- 导入jre包，使用工具类
            引入文件上传之后，request.getParameter 系列API 不再适用
            form表单中文乱码： getString("utf-8")
            上传文件名乱码： upload.setHeadEncoding(charset)
        |-- 上传时的一些问题：
            |-- 文件同名问题：
                |-- 可以添加UUID
                    UUID uuid = UUID.randomUUID();
                    fileName = uuid.toString() + "-" + fileName;
                    可以生成类似序列号的随机码
                |-- 可以使用唯一属性
                    身份证号
                    数据库id
            |-- 同一目录下文件数量过多的问题：
                |-- HashCode 散列
                    首先得到文件名的 HashCode，转为16进制的字符串
                    接着将每一位的字符串分别新建一个文件夹，一共8级目录，每一级目录最多有16个自目录
                    最后，在相应的目录下存放对应的文件
        |-- 代码重构





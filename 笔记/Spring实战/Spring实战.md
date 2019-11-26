# Spring的核心
Spring的功能底层都依赖于它的两个核心特性

依赖注入（dependency injection，DI）和面向切面编程（aspect-oriented programming，AOP）
## 简化Java开发
为了降低Java开发的复杂性，Spring采取了以下4种关键策略：

1. 基于POJO的轻量级和最小侵入性编程
2. 通过依赖注入和面向接口实现松耦合
3. 基于切面和惯例进行声明式编程
4. 通过切面和模板减少样式代码

## Spring容器
Spring容器使用DI管理构成应用的组件， 它会创建相互协作的组件之间的关联

Spring自带了多个容器实现，可以归为 两种不同的类型

* bean工厂（由org.springframework. beans. factory.eanFactory接口定义）是最简单的容器，提供基本的DI 支持
* 应用上下文 （由org.springframework.context.ApplicationContext 接口定义）基于BeanFactory构建，并提供应用框架级别的服务，例如 从属性文件解析文本信息以及发布应用事件给感兴趣的事件监听者
### bean的生命周期
1. bean对象的实例化
2. 封装属性，也就是设置properties中的属性值
3. 如果bean实现了BeanNameAware，则执行setBeanName方法,也就是bean中的id值
4. 如果实现BeanFactoryAware或者ApplicationContextAware ，需要设置setBeanFactory或者上下文对象setApplicationContext
5. 如果存在类实现BeanPostProcessor后处理bean，执行postProcessBeforeInitialization，可以在初始化之前执行一些方法
6. 如果bean实现了InitializingBean，则执行afterPropertiesSet，执行属性设置之后的操作
7. 调用<bean　init-method="">执行指定的初始化方法
8. 如果存在类实现BeanPostProcessor则执行postProcessAfterInitialization，执行初始化之后的操作
9. 执行自身的业务方法
10. 如果bean实现了DisposableBean，则执行spring的的销毁方法
11. 调用<bean　destory-method="">执行自定义的销毁方法。














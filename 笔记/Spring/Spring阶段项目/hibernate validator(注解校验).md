## 功能

Spring MVC 使用 hibernate validator 框架可以实现的功能：

* 注解 Java bean 声明校验规则
* 添加 message 错误信息源实现国际化配置 （返回一个错误信息的 key，locale）

## 基本使用

### 案例

使用注册登录的案例来使用 validation 框架解决验证问题

用户名不为空

密码成都至少6位 至多8位

```java
// 用户名不为空
private String username;

//密码长度6-8
private String password;
```

### 配置环境

#### 依赖

```xml
<dependency>
	<groupId>org.hibernate</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>5.1.0.Final</version>
</dependency>
<dependency>
	<groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>1.1.0.Final</version>
</dependency>
```

#### Hibernate-validation 和 Spring MVC 整合

```xml
<!-- 校验器 -->
<bean id="validator" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean">
    <property name="providerClass" value="org.hibernate.validator.HibernateValidator"/>
</bean>
```

```xml
<mvc:annotation-driven validator="validator"/>
```

### 验证字段合法性

在需要验证的 bean 里增加如下注解：

```java
@Size(max=6, message="客户名称请限制在6个字符内")
private String username;
```

在 Spring MVC 的参数类型前增加验证注解

```java
@RequestMapping("/login")
public Result login(@Valid User user, BindingResult bindingResult)
```

### 测试

```java
@RequestMapping("/login")
public Result login(@Valid User user, BindingResult bindingResult){
    
    // 验证失败了
    if (bindingResult.hasErrors()){
        
        FieldError fieldError = bindingResult.getFieldError();
        String field = fieldError.getField();
        String defaultMessage = fieldError.getDefaultMessage();
        
        System.out.println("field" + field);
        System.out.println("defaultMessage" + defaultMessage);
        return "/register.jsp"
        
    }
}
```

![image-20191201213805658](hibernate%20validator(%E6%B3%A8%E8%A7%A3%E6%A0%A1%E9%AA%8C).assets/image-20191201213805658.png)

## 常见注解

* @Null  被注释的元素必须为 null   

* @NotNull   被注释的元素必须不为 null   

* @Size(max=, min=)  被注释的元素的大小必须在指定的范围内   

* @AssertTrue   被注释的元素必须为 true   

* @AssertFalse   被注释的元素必须为 false   

* @Min(value)   被注释的元素必须是一个数字，其值必须大于等于指定的最小值   

* @Max(value)   被注释的元素必须是一个数字，其值必须小于等于指定的最大值   

* @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值   

* @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值   

* @Digits (integer, fraction)   被注释的元素必须是一个数字，其值必须在可接受的范围内   

* @Past  被注释的元素必须是一个过去的日期   

* @Future   被注释的元素必须是一个将来的日期   

* @Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式   

  **Hibernate Validator 附加的 constraint**

* @NotBlank(message =)  验证字符串非null，且长度必须大于0   

* @Email  被注释的元素必须是电子邮箱地址   

* @Length(min=,max=)  被注释的字符串的大小必须在指定的范围内   

* @NotEmpty  被注释的字符串的必须非空   

* @Range(min=,max=,message=)  被注释的元素必须在合适的范围内

### 


#自定义springmvc的一些配置

```java
@Configuration
@EnableWebMvc
public class WebmvcConfiguration extends WebMvcConfigurerAdapter {
    
}
```

- 继承WebMvcConfigurerAdapter类

##自定义静态资源路径

```java
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/webjars/**") .addResourceLocations("classpath:/META-INF/resources/webjars/");
}
```

```properties
#默认值为：/** 
spring.mvc.static-path-pattern= /**
#默认值为：classpath:/static/,classpath:/public/，... 
spring.resources.static-locations= classpath:/static/
```

##自定义拦截器放行路径

```java
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginInterceptor())
        .excludePathPatterns("/user/login","/api/**");
}
```

# 自定义异常信息

自定义异常类

```java
public class UserNotExistException extends RuntimeException {

   public UserNotExistException() {
      super("用户不存在");
   }
}
```

捕获异常

```java
@ControllerAdvice
public class MyExceptionHandler {
   @ExceptionHandler(Exception.class)
   public String handleException(Exception e, HttpServletRequest request) {
      Map<String, Object> map = new HashMap<>();
      //Integer statusCode = (Integer) request
      //          .getAttribute("javax.servlet.error.status_code");
      request.setAttribute("javax.servlet.error.status_code", 400);
      map.put("code","user.notexist");
      map.put("message", e.getMessage());
      request.setAttribute("ext",map);
      return "forward:/error";
   }
}
```

- 最后的return是为了自适应（根据不同访问返回页面或者json数据）
- 此时需要将自定义的数据保存到request里

补充异常返回信息

```java
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {
   @Override
   public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
      Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
      errorAttributes.put("company","D9ing");
      Map<String,Object> ext = (Map<String, Object>) requestAttributes.getAttribute("ext", 0);
      errorAttributes.put("ext",ext);
      return errorAttributes;
   }
}
```

- 增加一些自定义的信息
- 获取request域中的数据
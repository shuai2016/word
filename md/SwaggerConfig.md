```
SwaggerConfig
```

```
registry.addResourceHandler("swagger-ui.html") .addResourceLocations("classpath:/META-INF/resources/");
registry.addResourceHandler("/webjars/**") .addResourceLocations("classpath:/META-INF/resources/webjars/");
```

```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.4.0</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.4.0</version>
</dependency>
```
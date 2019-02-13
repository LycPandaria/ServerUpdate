# 为系统添加 Log
1. 为系统添加相应的 Log 依赖，在 pom.xml 中添加
```xml
<!-- 日志文件管理包 -->
<!-- log start -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.6.6</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.2.12</version>
</dependency>
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.2.12</version>
</dependency>
```

2. 添加 loj4.properties
```text
# 可以根据自己需求更改配置
# Global logging configuration
log4j.rootLogger=ERROR, stdout

# MyBatis logging configuration...
log4j.logger.com.ynrcc.hrm=DEBUG
log4j.logger.com.ynrcc.simpleserver=DEBUG
log4j.logger.com.ynrcc.simpleserver.dao.TrunkItemDao=DEBUG

# Console output
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```

3. 在 web.xml 中配置 Log 相应配置
```xml
<!-- log4g日志配置 -->
<context-param>
    <param-name>log4jConfigLocation</param-name>
    <param-value>classpath:properties/log4j.properties</param-value>
</context-param>
<listener>
    <description>log4g日志加载</description>
    <listener-class>org.springframework.web.util.Log4jConfigListener</listener-class>
</listener>
```
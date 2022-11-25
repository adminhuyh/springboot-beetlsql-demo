官网：
http://ibeetl.com/guide/#/beetlsql/

# BeetleSQL特点
**BeetSql是一个全功能DAO工具， 同时具有Hibernate 优点 & Mybatis优点功能，适用于承认以SQL为中心，同时又需求工具能自动能生成大量常用的SQL的应用。**

- 开发效率
1. 无需注解，自动使用大量内置SQL，轻易完成增删改查功能，节省50%的开发工作量
1. 数据模型支持Pojo，也支持Map/List这种快速模型，也支持混合模型
1. SQL 模板基于Beetl实现，更容易写和调试，以及扩展
1. 可以针对单个表(或者视图）代码生成pojo类和sql模版，甚至是整个数据库。能减少代码编写工作量
- 维护性
1. SQL 以更简洁的方式，Markdown方式集中管理，同时方便程序开发和数据库SQL调试。
1. 可以自动将sql文件映射为dao接口类
1. 灵活直观的支持支持一对一，一对多，多对多关系映射而不引入复杂的OR Mapping概念和技术。
1. 具备Interceptor功能，可以调试，性能诊断SQL，以及扩展其他功能
- 其他
1. 内置支持主从数据库支持的开源工具
1. 性能数倍于JPA，MyBatis
1. 支持跨数据库平台，开发者所需工作减少到最小，目前跨数据库支持mysql,postgres,oracle,sqlserver,h2,sqllite,DB2.
1. 高性能





# BeetlSQL结合SpringBoot使用

## 添加依赖

```xml
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>com.ibeetl</groupId>
            <artifactId>beetl-framework-starter</artifactId>
            <version>1.2.14.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
```

## 配置application.yml
```yml
################################## beetlsql
beetlsql:
  sqlPath:  /sql
  basePackage:  com.eli.springbootbeetlsqldemo.dao
  #mapper接口的后缀
  daoSuffix: Dao
  #是否向控制台输出执行的sql，默认为true
beetl-beetlsql:
  dev: true

################################## hikari
#数据源配置
spring:
  datasource:
    name: beetlsql
    url: jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=UTC
    username: root
    password: 123456
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver


```

## schema.sql
```sql
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
  age INT(11) NULL DEFAULT NULL COMMENT '年龄',
  email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (id)
);
```

## data.sql
```sql
INSERT INTO user (id, name, age, email) VALUES
  (1, 'Eli', 18, 'Eli@example.com'),
  (2, 'Jack', 10, 'Jack@example.com'),
  (3, 'Tom', 28, 'Tom@example.com'),
  (4, 'Sandy', 21, 'Sandy@example.com'),
  (5, 'Billie', 24, 'Billie@example.com');
```

## User.java
```java
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```
## UserDao.java
```java
@SqlResource("user")
public interface UserDao extends BaseMapper<User> {

    List<User> findByName(String name);
}
```

## user.md
```markdown
findByName
===
* 根据用户名查找
select id,name,age,email from user where true
    @if(!isEmpty(name)){
    	and 
    	name = #name#
    @}
```

## BeetlSQLApplication.java
```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Resource
    private UserDao userMapper;

    @RestController
    public class TestController {
        @GetMapping(value = "/users/{name}")
        public List<User> users(@PathVariable String  name) {
            return userMapper.findByName(name);
        }
        @GetMapping(value = "/users2/{name}")
        public List<User> usersBySQLManager(@PathVariable String  name) {
            return sql.lambdaQuery(User.class).andEq(User::getName,name).select("id","name","age","email");
        }

    }
}
```

## DataSourceConfig.java
```java
@Configuration
public class DataSourceConfig {

    @Bean(name="datasource")
    public DataSource datasource(Environment env) {

        HikariDataSource hds = new HikariDataSource();
        hds.setJdbcUrl(env.getProperty("spring.datasource.url"));
        hds.setUsername(env.getProperty("spring.datasource.username"));
        hds.setPassword(env.getProperty("spring.datasource.password"));
        hds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        return hds;
    }
}
```

## 测试
访问`http://localhost:8080/users/Eli`

![image.png](http://www.nick-hou.cn/upload/2020/04/image-02c145e47e3e488faedb0536414b796f.png)

访问`http://localhost:8080/users2/Eli`
![image.png](http://www.nick-hou.cn/upload/2020/04/image-d7732dcd7c7c4d72b15ec18f9cc71357.png)

# 项目源码地址
https://gitee.com/KimHX/springboot-beetlsql-demo.git

package com.eli.springbootbeetlsqldemo;

import com.eli.springbootbeetlsqldemo.bean.User;
import com.eli.springbootbeetlsqldemo.dao.UserDao;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Resource
    private UserDao userMapper;

    @Autowired
    SQLManager sql;


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

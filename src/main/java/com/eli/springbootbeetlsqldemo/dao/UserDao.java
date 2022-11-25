package com.eli.springbootbeetlsqldemo.dao;


import com.eli.springbootbeetlsqldemo.bean.User;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

@SqlResource("user")
public interface UserDao extends BaseMapper<User> {

    List<User> findByName(String name);
}

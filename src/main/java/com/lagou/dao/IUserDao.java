package com.lagou.dao;

import com.lagou.pojo.User;
import java.util.List;

/**
 * 用户dao层接口
 */
public interface IUserDao {

    //查询所有用户
    public List<User> findAll() throws Exception;


    //根据条件进行用户查询
    public User findByCondition(User user) throws Exception;

    //新增用户
    public void insertUser(User user);

    //修改用户
    public int updateUser(User user);

    //删除用户
    public int deleteUser(User user);

}

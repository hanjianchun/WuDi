package com.han.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.han.pojo.User;

public interface UserMapper {
	
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<User> getAllUser();
	
	/**
	 * 分页查询
	 * @param start
	 * @return
	 */
	public List<User> getUserListByPage(@Param(value="start") int start,@Param(value="size") int size);
	
	/**
	 * 插入用户
	 * @param user
	 * @return
	 */
	public int insertUser(User user);
	
	/**
	 * 根据ID删除用户
	 * @param id
	 * @return
	 */
	public int delUserById(String id);
	/**
	 * 通过id获取用户
	 * @param id
	 * @return
	 */
	public User getUserById(String id);
	
	public List<User> getUserByUser(User user);
	
	/**
	 * 获取用户总数量
	 * @return
	 */
	public int getUserCount();
}

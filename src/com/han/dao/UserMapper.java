package com.han.dao;

import java.util.List;

import com.han.pojo.User;

public interface UserMapper {
	
	public List<User> getAllUser();
	
	public List<User> getUserListByPage(int start);
	
	public int insertUser(User user);
	
	public int delUserById(String id);
	
	/**
	 * 获取用户总数量
	 * @return
	 */
	public int getUserCount();
}

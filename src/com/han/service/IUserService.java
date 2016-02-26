package com.han.service;

import java.util.List;

import com.han.pojo.User;
import com.han.utils.PageResult;

public interface IUserService {
	
	public PageResult getUserListByPage(PageResult pageResult);
	public int insertUser(User user);
	public int delUserById(String id);
	public List<User> getAllUser();
	
	public User getUserById(String id);
	
	public List<User> getUserByUser(User user);
	
	public String addUserList(List<User> userList);
	
	public void updateUser(User user);
}

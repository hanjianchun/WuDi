package com.han.service;

import java.util.List;

import com.han.pojo.User;
import com.han.utils.PageResult;

public interface IUserService {
	
	public PageResult getUserListByPage(PageResult pageResult);
	public int insertUser(User user);
	public int delUserById(String id);
	public List<User> getAllUser();
}

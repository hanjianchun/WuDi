package com.han.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.han.dao.UserMapper;
import com.han.pojo.User;
import com.han.service.IUserService;
import com.han.utils.MyBatisUtil;
import com.han.utils.PageResult;

public class UserServiceImpl implements IUserService {

	private static SqlSessionFactory sqlSessionFactory;

	static {
		sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
	}

	@Override
	public List<User> getAllUser() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<User> userList = null;
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			userList = userMapper.getAllUser();
		} finally {
			sqlSession.close();
		}
		return userList;
	}

	@Override
	public PageResult getUserListByPage(PageResult pageResult) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			int userCount = userMapper.getUserCount();
			if(userCount != 0){
				pageResult.setTotal(userCount);
				List<User> userList = userMapper.getUserListByPage(pageResult.getCurPage()*pageResult.getSize(),pageResult.getSize());
				pageResult.setList(userList);
				pageResult.setCurTotal(userList.size());
			}
		} finally {
			sqlSession.close();
		}
		return pageResult;
	}

	@Override
	public int insertUser(User user) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int count = 0;
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			count = userMapper.insertUser(user);
			sqlSession.commit();
		} catch (Exception e) {
			System.out.println("hehe");
		} finally {
			sqlSession.close();
		}
		return count;
	}

	@Override
	public int delUserById(String id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int count = 0;
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			count = userMapper.delUserById(id);
			sqlSession.commit();
		} catch (Exception e) {
			System.out.println("hehe");
		} finally {
			sqlSession.close();
		}
		return count;
	}

	@Override
	public User getUserById(String id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = null;
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			user = userMapper.getUserById(id);
		} finally {
			sqlSession.close();
		}
		return user;
	}

	@Override
	public List<User> getUserByUser(User user) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<User> userList ;
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			userList = userMapper.getUserByUser(user);
		} finally {
			sqlSession.close();
		}
		return userList;
	}

	@Override
	public String addUserList(List<User> userList) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<User> haveUser = new ArrayList<User>();
		try {
			for(User user : userList){
				UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
				User userParam = new User();
				userParam.setUser_name(user.getUser_name());
				List<User> userAll = getUserByUser(user);
				if(null == userAll || userAll.size()==0)
					userMapper.insertUser(user);
				else
					haveUser.add(user);
			}
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
			return "导入失败，请检查文件格式是否正确！";
		} finally {
			sqlSession.close();
		}
		String msg = "";
		if(haveUser.size()==0)
			msg = "新增"+(userList.size()-haveUser.size())+"个";
		else
			msg = "新增"+(userList.size()-haveUser.size())+"个,有"+haveUser.size()+"个已经存在重名已忽略！";
		return msg;
	}

	@Override
	public void updateUser(User user) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			userMapper.updateUser(user);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
}

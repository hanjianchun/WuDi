package com.han.service.impl;

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
				List<User> userList = userMapper.getUserListByPage(pageResult.getCurPage()*15+pageResult.getCurTotal());
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

}

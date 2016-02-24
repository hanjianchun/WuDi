package com.han.junit;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.han.dao.UserMapper;
import com.han.pojo.User;
import com.han.service.IUserService;
import com.han.service.impl.UserServiceImpl;
import com.han.utils.MyBatisUtil;
import com.han.utils.PageResult;
import com.han.utils.ScreenUtils;

/**
 * 测试类
 * 
 * @author Han
 * 
 */
public class JunitTest {

	/**
	 * 测试com.han.utils.ScreentUtils
	 */
	@Test
	public void testScreenUtils() {
		System.out.println(ScreenUtils.getScreenHeight() + "  "
				+ ScreenUtils.getScreenWidth());
	}

	public static void main(String[] args) throws IOException {
		
	}

	public static void getUser() {
//		SqlSessionFactory sqlSessionFactory = MyBatisUtil
//				.getSqlSessionFactory();
//		SqlSession sqlSession = sqlSessionFactory.openSession();
//		try {
//			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//			List<User> user = userMapper.getUserListByPage();
//			for (User u : user) {
//				System.out.println(u.toString());
//			}
//		} finally {
//			sqlSession.close();
//		}
	}

	@Test
	public void get() {
		IUserService userService = new UserServiceImpl();
		for(int i = 0;i<1000;i++){
			userService.insertUser(new User("B"+i,"3","3","3","3","3"));
		}
	}
	
	@Test
	public void getUserById(){
		IUserService userService = new UserServiceImpl();
		System.out.println(userService.getUserById("40"));
	}
	
	@Test
	public void getUserByUser(){
		IUserService userService = new UserServiceImpl();
		User user = new User();
		user.setUser_name("傻逼");
		System.out.println(userService.getUserByUser(user));
	}
}

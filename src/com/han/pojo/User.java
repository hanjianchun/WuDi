package com.han.pojo;

import java.io.Serializable;

import com.han.utils.Constrats;

/**
 * 用户实体类
 * @author Han
 *
 */
public class User implements Serializable{
	private String id;//id
	private String pid;//上级ID
	private String user_num;//编号
	private String user_name;//姓名
	private String user_age;//年龄
	private String user_grade;//等级
	private String user_sex;//性别
	private String user_money;//金额
	private String user_state;//状态
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_num() {
		return user_num;
	}
	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_age() {
		return user_age;
	}
	public void setUser_age(String user_age) {
		this.user_age = user_age;
	}
	public String getUser_grade() {
		return user_grade;
	}
	public void setUser_grade(String user_grade) {
		this.user_grade = user_grade;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}
	public String getUser_money() {
		return user_money;
	}
	public void setUser_money(String user_money) {
		this.user_money = user_money;
	}
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getUser_state() {
		return user_state;
	}
	public void setUser_state(String user_state) {
		this.user_state = user_state;
	}
	public User() {
		super();
	}
	public String[] toStringArray(User u){
		return new String[]{u.getId(),u.getPid(),u.getUser_num(),u.getUser_name(),u.getUser_age(),u.getUser_grade(),u.getUser_sex().equals("0")?"男":"女",u.getUser_money()};
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", pid=" + pid + ", user_num=" + user_num
				+ ", user_name=" + user_name + ", user_age=" + user_age
				+ ", user_grade=" + user_grade + ", user_sex=" + user_sex
				+ ", user_money=" + user_money + "]";
	}
	public User( String user_num, String user_name, String user_age,
			String user_grade, String user_sex, String user_money) {
		super();
		this.user_num = user_num;
		this.user_name = user_name;
		this.user_age = user_age;
		this.user_grade = user_grade;
		this.user_sex = user_sex;
		this.user_money = user_money;
	}
	
	
}

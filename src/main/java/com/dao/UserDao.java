package com.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.UserBean;

@Repository
public class UserDao {
	@Autowired
	JdbcTemplate stmt;

	public void addUser(UserBean user) {
		stmt.update(
				"insert into users (username,phonenum,emailid,password,roleid,createdon,pincode,address) values (?,?,?,?,2,CURRENT_DATE,?,?)",
				user.getUsername(), user.getPhonenum(), user.getEmailid(), user.getPassword(), user.getPincode(),
				user.getAddress());
	}
	public List<UserBean> getAllUsers(){
		return stmt.query("select * from users", new BeanPropertyRowMapper<>(UserBean.class));
	}
	
	public UserBean authenticateUser (UserBean user) {
		return stmt.queryForObject("select * from users where emailid = ? and password = ?", new BeanPropertyRowMapper<UserBean>(UserBean.class), new Object[] {user.getEmailid(),user.getPassword()} );
	}
	
	public UserBean addAuthToken(UserBean user) {
		stmt.update("update users set authtoken = ? where email = ? and password = ?",user.getAuthtoken(),user.getEmailid(),user.getPassword());
		return user;
	}
}

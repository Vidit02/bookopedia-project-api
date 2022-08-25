package com.dao;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.LoginBean;
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

	public UserBean authenticateUser (LoginBean user) {
		List<UserBean> users =  stmt.query("select * from users where emailid = ? and password = ?", new BeanPropertyRowMapper<UserBean>(UserBean.class), new Object[] {user.getEmailid(),user.getPassword()} );
//		if(userDetail == null) {
//			return null;
//		} else {
//			return userDetail;
//		}
		if(users.size() == 0) {
			return null;
		} else {
			return users.get(0);
		}
	}
	
	
	public UserBean addAuthToken(UserBean user) {
		stmt.update("update users set authtoken = ? where emailid = ? and password = ?",user.getAuthtoken(),user.getEmailid(),user.getPassword());
		return user;
	}
	
	public Boolean searchByEmail(LoginBean user) {
		List<UserBean> users = stmt.query("select * from users where emailid = ?" , new BeanPropertyRowMapper<UserBean>(UserBean.class),new Object[] {user.getEmailid()});
		if(users.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean isEmail(String emailid) {
		List<UserBean> users = stmt.query("select * from users where emailid = ?" , new BeanPropertyRowMapper<UserBean>(UserBean.class),new Object[] {emailid});
		if(users.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public UserBean getUserByAuthtoken(String authtoken, int userid) {
		List<UserBean> user = stmt.query("select * from users where authtoken = ? and userid = ?", new BeanPropertyRowMapper<UserBean>(UserBean.class), new Object[] {authtoken,userid} );
		if(user.size() == 0) {
			return null;
		} else {
			return user.get(0);
		}
	}
}

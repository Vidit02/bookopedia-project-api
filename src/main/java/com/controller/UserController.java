package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.UserBean;
import com.dao.UserDao;

@RestController
@CrossOrigin
public class UserController {
	@Autowired
	UserDao userDao;
	
	@PostMapping("/signup")
	public UserBean addUser(@RequestBody UserBean user) {
		userDao.addUser(user);
		return user;
	}
	
	@GetMapping("/users")
	public List<UserBean> listUser(){
		return userDao.getAllUsers();
	}
	
	@PostMapping("/login")
	public UserBean authenticateUser(@RequestBody UserBean user) {
		return userDao.authenticateUser(user);
	}
	
	
}

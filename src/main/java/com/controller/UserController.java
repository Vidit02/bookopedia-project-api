package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.UserBean;
import com.dao.UserDao;
import com.service.GenerateAuthToken;

@RestController
@CrossOrigin
public class UserController {
	@Autowired
	UserDao userDao;
	
	@Autowired
	GenerateAuthToken authtoken;
	
	@PostMapping(path = "/signup")
	public UserBean addUser(@RequestBody UserBean user) {
		userDao.addUser(user);
		System.out.println(user.getUsername() + " " + user.getEmailid() + " " + user.getPassword());
		return user;
	}
	
	@GetMapping("/private/users")
	public List<UserBean> listUser(){
		return userDao.getAllUsers();
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserBean user) {
		UserBean newuser = userDao.authenticateUser(user);
		if(newuser == null) {
			ResponseEntity<?> resp = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			return resp;
		} else {
			if(newuser.getAuthtoken() == null) {
				System.out.println("Authtoken not generated");
				newuser.setAuthtoken(authtoken.generateToken(16));
				newuser = userDao.addAuthToken(newuser);
				System.out.println("Auth Token generated : " + newuser);
			}
			return ResponseEntity.ok(newuser);
		}
		
	}
	
	
}

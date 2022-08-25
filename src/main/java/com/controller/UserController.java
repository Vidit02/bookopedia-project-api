package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bean.LoginBean;
import com.bean.UserBean;
import com.dao.UserDao;
import com.service.EmailService;
import com.service.GenerateAuthToken;

@RestController
@CrossOrigin
public class UserController {
	@Autowired
	UserDao userDao;
	
	@Autowired
	GenerateAuthToken authtoken;
	
	@Autowired
	EmailService emailService;
	
	//Can be used using json object only
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
	
	
//	@PostMapping("/login")
//	public UserResponse<?> loginUser(@RequestBody LoginBean user) {
//		UserBean newuser = userDao.authenticateUser(user);
//		UserResponse<UserBean> res = new UserResponse<>();  
//		if(newuser == null) {
//			ResponseEntity.status(HttpStatus.UNAUTHORIZED);
//			res.setStatus(401);
//			res.setMsg("Unauthorized User");
//			return res;
//		} else {
//			if(newuser.getAuhtoken() == null) {
//				System.out.println("Authtoken not generated");
//				newuser.setAuthtoken(authtoken.generateToken(16));
//				newuser = userDao.addAuthToken(newuser);
//				System.out.println("Auth Token generated : " + newuser);
//			}
//			res.setStatus(200);
//			res.setData(newuser);
//			res .setMsg("Successfully Logged in");
//			
//			return res;
//		}
//		
//	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginBean user) {
		UserBean newuser = userDao.authenticateUser(user);
		if(newuser == null) {
			
			ResponseEntity<?> resp = new ResponseEntity<>("Unauthorized User",HttpStatus.UNAUTHORIZED);
//			return ResponseEntity.notFound().build()''
			return resp;
		} else {
			if(newuser.getAuthtoken() == null) {
				System.out.println("Authtoken not generated");
				newuser.setAuthtoken(authtoken.generateToken(16));
				newuser = userDao.addAuthToken(newuser);
				System.out.println("Auth Token generated : " + newuser.getAuthtoken());
			}
			return ResponseEntity.ok(newuser);
		}
		
	}
	
	@PostMapping("/searchemail")
	public ResponseEntity<?> searchEmail(@RequestBody LoginBean user) {
		System.out.println("email " + user.getEmailid());
		Boolean isEmail = userDao.searchByEmail(user);
		if(isEmail) {
			return ResponseEntity.ok(user);
		} else {
			ResponseEntity<?> resp = new ResponseEntity<>("User Not Found",HttpStatus.UNAUTHORIZED);
			return resp;
		}
	}
	
	@PostMapping("/emailcheck")
	public ResponseEntity<?> checkEmail(@RequestBody LoginBean user) {
		System.out.println("email " + user.getEmailid());
		Boolean isEmail = userDao.isEmail(user.getEmailid());
		if(isEmail) {
			System.out.println("This is true");
			return ResponseEntity.ok("Ok");
		} else {
			System.out.println("This is false");
			ResponseEntity<?> resp = new ResponseEntity<>("User Not Found",HttpStatus.UNAUTHORIZED);
			return resp;
		}
	}
	
	@PostMapping("/getotp")
	public ResponseEntity<?> generateNewOtp(@RequestBody LoginBean user) {
		String otp = authtoken.generateToken(6);
		Boolean isSend = emailService.sendEmailForOtp(user.getEmailid(),otp);
//		try {
//			isSend = emailService.sendEmailForOtp(user.getEmailid());
////			return ResponseEntity.ok(otp);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
////			ResponseEntity<?> resp = new ResponseEntity<>("Unauthorized User",HttpStatus.UNAUTHORIZED);
////			return resp;
//		}
		
		if(isSend) {
			return ResponseEntity.ok(otp);
		} else {
			ResponseEntity<?> resp = new ResponseEntity<>("Unauthorized User",HttpStatus.UNAUTHORIZED);
			return resp;
		}
		
	}
	
	@GetMapping("/getuserdata")
	public ResponseEntity<?> getUserData(@RequestHeader("authtoken") String authtoken , @RequestHeader("userid") int userid){
		UserBean user = userDao.getUserByAuthtoken(authtoken,userid);
		if(user != null) {
			return ResponseEntity.ok(user);
		} else {
			ResponseEntity<?> resp = new ResponseEntity<>("Unauthorized User",HttpStatus.UNAUTHORIZED);
			return resp;
		}
	}
	
}

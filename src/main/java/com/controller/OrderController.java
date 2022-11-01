package com.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bean.BookBean;
import com.bean.CartBean;
import com.bean.OrderBean;
import com.bean.UserBean;
import com.bean.UserResponse;
import com.dao.BookRepository;
import com.dao.CartRepository;
import com.dao.OrderRepository;
import com.dao.UserDao;

@CrossOrigin
@RestController
@EnableWebMvc
public class OrderController {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	UserDao userDao;
	
	@PostMapping("/placeorder/{id}/{authtoken}")
	public UserResponse<?> placeOrder(@PathVariable("id") Integer userId , @PathVariable("authtoken") String authToken){
//		int newUser = Integer.parseInt(userId);
		int newUser = userId;
		UserBean user = userDao.getUserByAuthtoken(authToken, newUser);
		if(user == null) {
			UserResponse<String> resp = new UserResponse<>();
			resp.setStatus(-1);
			resp.setMsg("User not authenticated");
			resp.setData("User not authenticated");
			return resp;
		} else {
			Integer cartId = user.getCartid();
			if(cartId == null) {
				UserResponse<String> resp = new UserResponse<>();
				resp.setStatus(-1);
				resp.setMsg("Cart not found");
				resp.setData("Cart id is wrong");
				return resp;
			} else {
				Integer total = 0;
				OrderBean order = new OrderBean();
				CartBean cart = cartRepository.getById(cartId);
				List<String> cartBooks = new ArrayList<>(Arrays.asList(cart.getCartitems().split(",")));
				for(String b : cartBooks) {
					BookBean newBook =  bookRepository.getReferenceById(Integer.parseInt(b));
					total = total + newBook.getPrice();
				}
				order.setOrderitems(cart.getCartitems());
				order.setTotalamount(total);
				order.setUserid(newUser);
				orderRepository.save(order);
				UserResponse<Integer> resp = new UserResponse<>();
				cartRepository.delete(cart);
				user.setCartid(null);
				userDao.updateCartid(newUser);
				resp.setStatus(200);
				resp.setMsg("Order Placed");
				resp.setData(order.getOrderid());
				return resp;
			}
		}
	}
}

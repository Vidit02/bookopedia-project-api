package com.controller;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.BookBean;
import com.bean.CartBean;
import com.bean.UserBean;
import com.bean.UserResponse;
import com.dao.BookRepository;
import com.dao.CartRepository;
import com.dao.UserDao;

@CrossOrigin
@RestController
@RequestMapping("/private")
public class CartController {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserDao userDao;
	
	@PostMapping("/addBookToCart")
	public ResponseEntity<?> addBookToCart(@RequestBody BookBean book,@RequestHeader("authToken") String authToken , @RequestHeader("userId") Integer userId) {
		UserBean user = userDao.getUserByAuthtoken(authToken, userId);
		System.out.println(book.getId()+"wejfwnoex");
		Integer cartid = user.getCartid();
		BookBean newBook = null;
		List<BookBean> booksdata = jdbcTemplate.query("select * from books where id = ?", new BeanPropertyRowMapper<BookBean>(BookBean.class),new Object[]{book.getId()});
		if(booksdata.size() != 0 ) {
			newBook = booksdata.get(0);
		}
		if(newBook == null) {
			ResponseEntity<?> resp = new ResponseEntity<>("Book not found" , HttpStatus.BAD_REQUEST);
			return resp;
		} else {
			if(cartid == null) {
				CartBean cart = new CartBean();
				String pro = book.getId() + ",";
				cart.setCartitems(pro);
				cart.setUserid(userId);
				cartRepository.save(cart);
				userDao.updateUser(userId, cart.getCartid());
				return ResponseEntity.ok("Book is added");
			} else {
				CartBean cart = cartRepository.getReferenceById(cartid);
				String pro = cart.getCartitems();
				List<String> cartBooks = new ArrayList<>(Arrays.asList(pro.split(",")));
				Integer isAdded = -1;
				isAdded = cartBooks.indexOf(String.valueOf(book.getId()));
				System.out.println(isAdded);
				if(isAdded == -1) {
					pro = pro + book.getId() + ",";
					cart.setCartitems(pro);
					cartRepository.save(cart);
					return ResponseEntity.ok("Book is added");
				} else {
					return ResponseEntity.ok("Book already added...");
				}
			}
		}
	}
}

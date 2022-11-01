package com.controller;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bean.BackCoverDb;
import com.bean.BookBean;
import com.bean.CartBean;
import com.bean.FrontCoverDb;
import com.bean.UserBean;
import com.bean.UserResponse;
import com.dao.BackCoverRepository;
import com.dao.BookRepository;
import com.dao.CartRepository;
import com.dao.FrontCoverRepository;
import com.dao.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@CrossOrigin
@RestController
@EnableWebMvc
public class CartController {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private FrontCoverRepository frontCoverRepository;
	
	@Autowired
	private BackCoverRepository backCoverRepository;
	
	@Autowired
	private UserDao userDao;
	
	@PostMapping("/addBookToCart")
	public UserResponse<?> addBookToCart(@RequestBody BookBean book,@RequestHeader("authToken") String authToken , @RequestHeader("userId") Integer userId) {
		UserBean user = userDao.getUserByAuthtoken(authToken, userId);
		System.out.println(book.getId()+"wejfwnoex");
		Integer cartid = user.getCartid();
		BookBean newBook = null;
		List<BookBean> booksdata = jdbcTemplate.query("select * from books where id = ?", new BeanPropertyRowMapper<BookBean>(BookBean.class),new Object[]{book.getId()});
		if(booksdata.size() != 0 ) {
			newBook = booksdata.get(0);
		}
		if(newBook == null) {
//			ResponseEntity<?> = new ResponseEntity<>("Book not found" , HttpStatus.BAD_REQUEST);
//			return resp;
			UserResponse<String> resp = new UserResponse<>();
			resp.setData(null);
			resp.setMsg("Book not found");
			resp.setStatus(401);
			return resp;
		} else {
			if(cartid == null) {
				CartBean cart = new CartBean();
				String pro = book.getId() + ",";
				cart.setCartitems(pro);
				cart.setUserid(userId);
				cartRepository.save(cart);
				userDao.updateUser(userId, cart.getCartid());
				UserResponse<String> resp = new UserResponse<>();
				resp.setData("Book is added");
				resp.setMsg("Book found and added");
				resp.setStatus(200);
				return resp;
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
					UserResponse<String> resp = new UserResponse<>();
					resp.setData("Book is added");
					resp.setMsg("Book found and added");
					resp.setStatus(200);
					return resp;
				} else {
					UserResponse<String> resp = new UserResponse<>();
					resp.setData("Book is already added");
					resp.setMsg("Book found and already added");
					resp.setStatus(300);
					return resp;
				}
			}
		}
	}
	
	@GetMapping("/numOfPro")
	public UserResponse<?> getNumberofPro(@RequestHeader("authToken") String authToken, @RequestHeader("userId") String userid) {
		int userId = Integer.parseInt(userid);
		UserBean user = userDao.getUserByAuthtoken(authToken, userId);	
		if(user == null) {
			UserResponse<String> resp = new UserResponse<>();
			resp.setStatus(-1);
			resp.setMsg("User incorrect");
			resp.setData("Cart id is wrong");
			return resp;
		} else {
			Integer cartid = user.getCartid();
			if(cartid == null || user.getCartid() != cartid) {
				UserResponse<String> resp = new UserResponse<>();
				resp.setStatus(-1);
				resp.setMsg("Cart not found");
				resp.setData("Cart id is wrong");
				return resp;
			} else {
				CartBean cart1 = cartRepository.getReferenceById(cartid);
				String pro = cart1.getCartitems();
				List<String> cartBooks = new ArrayList<>(Arrays.asList(pro.split(",")));
				Integer length = cartBooks.size();
				UserResponse<Integer> resp = new UserResponse<>();
				resp.setStatus(200);
				resp.setMsg("Cart found");
				resp.setData(length);
				return resp;
			}
		}
	}
	
	@GetMapping("/getPro")
	public UserResponse<?> getProductById(@RequestHeader("authToken") String authToken,@RequestHeader("userId") String userId) {
		int newUser = Integer.parseInt(userId);
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
				List<Integer> bookids = new ArrayList<Integer>();
				CartBean cart1 = cartRepository.getReferenceById(cartId);
				String bookStr = cart1.getCartitems();
				List<String> cartBooks = new ArrayList<>(Arrays.asList(bookStr.split(",")));
				for(String b : cartBooks) {
					bookids.add(Integer.parseInt(b));
				}
				List<BookBean> books = bookRepository.findAllById(bookids);
				for(BookBean book : books) {
					if(book.getFrontcover()!=null) {
						FrontCoverDb frontCoverDb = frontCoverRepository.findById(book.getFrontcover()).get();
						book.setFrontcover(Base64.getEncoder().encodeToString(frontCoverDb.getData()));
					}
					if(book.getBackcover()!=null) {
						BackCoverDb backCoverDb = backCoverRepository.findById(book.getBackcover()).get();
						book.setBackcover(Base64.getEncoder().encodeToString(backCoverDb.getData()));
					}
				}
				UserResponse<List<BookBean>> resp = new UserResponse<>();
				resp.setStatus(200);
				resp.setMsg("All Products found");
				resp.setData(books);
				return resp;
			}
		}
	}

	@GetMapping("/getTotalPrice")
	public UserResponse<?> getTotalCartPrice(@RequestHeader("authToken") String authtoken , @RequestHeader("userId") String userid){
		int newUser = Integer.parseInt(userid);
		UserBean user = userDao.getUserByAuthtoken(authtoken, newUser);
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
				CartBean cart1 = cartRepository.getReferenceById(cartId);
				String bookStr = cart1.getCartitems();
				List<String> cartBooks = new ArrayList<>(Arrays.asList(bookStr.split(",")));
				for(String b : cartBooks) {
					BookBean newBook =  bookRepository.getReferenceById(Integer.parseInt(b));
					total = total + newBook.getPrice();
				}
				UserResponse<Integer> resp = new UserResponse<>();
				resp.setStatus(200);
				resp.setMsg("Total Price");
				resp.setData(total);
				return resp;
			}
		}
	}
}

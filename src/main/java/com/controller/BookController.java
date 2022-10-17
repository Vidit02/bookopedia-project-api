package com.controller;

import java.util.Base64; 
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bean.BackCoverDb;
import com.bean.BookBean;
import com.bean.FrontCoverDb;
import com.bean.UserResponse;
import com.dao.BackCoverRepository;
import com.dao.BookRepository;
import com.dao.FrontCoverRepository;

@CrossOrigin
@RestController
public class BookController {
	
	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	FrontCoverRepository frontCoverRepository;
	
	@Autowired
	BackCoverRepository backCoverRepository;
	
	@PostMapping("/addbook")
	public BookBean addbook(@RequestBody BookBean book,@RequestHeader("userid") Integer userid) {
		book.setIsavailable(true);
		book.setUserid(userid);
		System.out.println(book.getUserid());
		return bookRepo.save(book);
	}
	
	@GetMapping("/products")
	public List<BookBean> getAllBooks() {
		List<BookBean> books = bookRepo.findAll();
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
		return books;
	}
	
	@GetMapping("/product/{id}")
	public UserResponse<?> getBookById(@PathVariable Integer id ){
		Optional<BookBean> book = bookRepo.findById(id);
		if(book.isPresent()) {
			UserResponse<BookBean> resp = new UserResponse<>();
			BookBean newBook = book.get();
			FrontCoverDb frontcover = frontCoverRepository.findById(newBook.getFrontcover()).get();
			BackCoverDb backcover = backCoverRepository.findById(newBook.getBackcover()).get();
			newBook.setFrontcover(Base64.getEncoder().encodeToString(frontcover.getData()));
			newBook.setFrontcover(Base64.getEncoder().encodeToString(backcover.getData()));
			resp.setData(book.get());
			resp.setMsg("Book found");
			resp.setStatus(200);
			return resp;
		} else {
			UserResponse<String> resp = new UserResponse<>();
			resp.setData("Not found");
			resp.setMsg("Book is not found");
			resp.setStatus(401);
			return resp;
		}
	}
}

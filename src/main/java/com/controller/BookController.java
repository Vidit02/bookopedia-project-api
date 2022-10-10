package com.controller;

import java.util.List;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.BookBean;
import com.dao.BookRepository;

@CrossOrigin
@RestController
public class BookController {
	
	@Autowired
	BookRepository bookRepo;
	
	@PostMapping("/addbook")
	public BookBean addbook(@RequestBody BookBean book) {
		book.setIsavailable(true);
		return bookRepo.save(book);
	}
	
	@GetMapping("/products")
	public List<BookBean> getAllBooks() {
		return bookRepo.findAll();
	}
}

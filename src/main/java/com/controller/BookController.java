package com.controller;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bean.BookBean;
import com.dao.BookRepository;


@RestController
public class BookController {
	
	@Autowired
	BookRepository bookRepo;
	
	@PostMapping("/addbook")
	public BookBean addbook(@RequestBody BookBean book) {
		return bookRepo.save(book);
	}
}

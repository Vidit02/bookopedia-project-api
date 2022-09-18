package com.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.bean.BackCoverBook;
import com.bean.BookCoverBean;
import com.bean.FilleBean;
import com.bean.UserBean;
import com.dao.BookCoverRepository;
import com.dao.UserDao;


@CrossOrigin
@RestController
public class FileController {

	@Autowired
	private BookCoverRepository bookCoverRepository;
	
	@Autowired
	private UserDao userDao;
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(FilleBean book , @RequestHeader("authToken") String authtoken , @RequestHeader("userid") int userid) throws IOException {
		UserBean user = userDao.getUserByAuthtoken(authtoken, userid);
		
//		System.out.println("book :" + book.getName() + "fronstcober : " + book.getFrontcover() + book.getBackcover());
		BookCoverBean bookBean = new BookCoverBean( book.getFrontcover().getBytes(), book.getBackcover().getBytes(), user.getUserid());
		bookCoverRepository.save(bookBean);
		return ResponseEntity.ok(bookBean.getId());
	}
	@PostMapping("/frontcoverupload")
	public ResponseEntity<?> uploadFrontCoverFile(FilleBean book , @RequestHeader("authToken") String authtoken , @RequestHeader("userid") int userid) throws IOException {
		UserBean user = userDao.getUserByAuthtoken(authtoken, userid);
		
//		System.out.println("book :" + book.getName() + "fronstcober : " + book.getFrontcover() + book.getBackcover());
		BookCoverBean bookBean = new BookCoverBean( book.getFrontcover().getBytes(), user.getUserid());
		bookCoverRepository.save(bookBean);
		return ResponseEntity.ok(bookBean.getId());
	}
	
	@PostMapping("/backcoverupload")
	public ResponseEntity<?> uploadBackCoverFile(FilleBean book , @RequestHeader("authToken") String authtoken , @RequestHeader("userid") int userid , @RequestHeader("imgdata") String imgdata) throws IOException {
		UserBean user = userDao.getUserByAuthtoken(authtoken, userid);
//		BookCoverBean bookBean = new BookCoverBean(  user.getUserid(),book.getBackcover().getBytes());
//		System.out.println("book :" + book.getName() + "fronstcober : " + book.getFrontcover() + book.getBackcover());
//		bookCoverRepository.updateBackcover(bookBean.getBackcover(), book.getImgid());
//		BookCoverBean bookBean = new BookCoverBean(user.getUserid() , book.getBackcover().getBytes());
//		BookCoverBean updateBook = bookCoverRepository.getById(imgdata);
//		updateBook.setBackcover(bookBean.getBackcover());
		BookCoverBean doneBook = bookCoverRepository.findById(imgdata).get();
		BookCoverBean bookBean = new BookCoverBean(user.getUserid(),doneBook.getFrontcover() , book.getBackcover().getBytes(),imgdata);
		bookCoverRepository.save(bookBean);
		return ResponseEntity.ok(bookBean.getId());
	}
	
	@GetMapping("/frontcover/{id}")
	public ResponseEntity<byte[]> getFrontCover (@PathVariable String id) {
		BookCoverBean file =  bookCoverRepository.findById(id).get();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(file.getBackcover());
	}
	
	@GetMapping("/backcover/{id}")
	public ResponseEntity<byte[]> getBackCover (@PathVariable String id) {
		BookCoverBean file =  bookCoverRepository.findById(id).get();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(file.getBackcover());
	}
	
	
}

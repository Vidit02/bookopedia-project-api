package com.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bean.BackCoverDb;
import com.bean.FileBean;
import com.bean.FrontCoverDb;
import com.bean.UserBean;
import com.dao.BackCoverRepository;
import com.dao.FrontCoverRepository;
import com.dao.UserDao;
import com.dropbox.core.DbxException;


@CrossOrigin
@RestController
public class FileController {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	private FrontCoverRepository frontCoverRepository;
	
	@Autowired
	private BackCoverRepository backCoverRepository;
	
	@Autowired
	UserDao userDao;

	@PostMapping("/uploadfrontcover")
	public ResponseEntity<?> uploadFrontCover(FileBean fileBean,@RequestHeader("authToken") String authtoken,@RequestHeader("userId") int userid) throws IOException{
		UserBean user = userDao.getUserByAuthtoken(authtoken, userid);
		FrontCoverDb frontCover = new FrontCoverDb(fileBean.getFile().getBytes(), userid);
		frontCoverRepository.save(frontCover);
		return ResponseEntity.ok(frontCover.getId());
	}
	
	@GetMapping("/frontcover/{id}")
	public ResponseEntity<byte[]> getFrontCover (@PathVariable String id) {
		FrontCoverDb file =  frontCoverRepository.findById(id).get();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(file.getData());
	}
	
	@PostMapping("/uploadbackcover")
	public ResponseEntity<?> uploadBackCover(FileBean fileBean, @RequestHeader("authToken") String authtoken,@RequestHeader("userId") int userid) throws IOException{
		UserBean user = userDao.getUserByAuthtoken(authtoken, userid);
		BackCoverDb backCover = new BackCoverDb(fileBean.getFile().getBytes(),userid);
		backCoverRepository.save(backCover);
		return ResponseEntity.ok(backCover.getId());
	}
	
	@GetMapping("/backcover/{id}")
	public ResponseEntity<byte[]> getBackCover (@PathVariable String id) {
		BackCoverDb file =  backCoverRepository.findById(id).get();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(file.getData());
	}
	
//	DropDownUpload dropDown;
//	
//	
//	@GetMapping("/download")
//	public ResponseEntity<?> getfrontcover() throws IOException,DbxException{
//		OutputStream outer = dropDown.upload();
//		System.out.println(outer.toString());
//		OutputStream output = new OutputStream() {
//		    private StringBuilder string = new StringBuilder();
//
//		    @Override
//		    public void write(int b) throws IOException {
//		        this.string.append((char) b );
//		    }
//
//		    //Netbeans IDE automatically overrides this toString()
//		    public String toString() {
//		        return this.string.toString();
//		    }
//		};
//		System.out.println(Arrays.toString(bytes));
//		return ResponseEntity.status(HttpStatus.OK).body("ok");
//	}
//	@Autowired
//	private BookCoverRepository bookCoverRepository;
//
//	@Autowired
//	private UserDao userDao;
//	
//	@PostMapping("/upload")
//	public ResponseEntity<?> uploadFile(FilleBean book , @RequestHeader("authToken") String authtoken , @RequestHeader("userid") int userid) throws IOException {
//		UserBean user = userDao.getUserByAuthtoken(authtoken, userid);
//		
////		System.out.println("book :" + book.getName() + "fronstcober : " + book.getFrontcover() + book.getBackcover());
//		BookCoverBean bookBean = new BookCoverBean( book.getFrontcover().getBytes(), book.getBackcover().getBytes(), user.getUserid());
//		bookCoverRepository.save(bookBean);
//		return ResponseEntity.ok(bookBean.getId());
//	}
//	@PostMapping("/frontcoverupload")
//	public ResponseEntity<?> uploadFrontCoverFile(FilleBean book , @RequestHeader("authToken") String authtoken , @RequestHeader("userid") int userid) throws IOException {
//		UserBean user = userDao.getUserByAuthtoken(authtoken, userid);
//		
////		System.out.println("book :" + book.getName() + "fronstcober : " + book.getFrontcover() + book.getBackcover());
//		BookCoverBean bookBean = new BookCoverBean( book.getFrontcover().getBytes(), user.getUserid());
//		bookCoverRepository.save(bookBean);
//		return ResponseEntity.ok(bookBean.getId());
//	}
//	
//	@PostMapping("/backcoverupload")
//	public ResponseEntity<?> uploadBackCoverFile(FilleBean book , @RequestHeader("authToken") String authtoken , @RequestHeader("userid") int userid , @RequestHeader("imgdata") String imgdata) throws IOException {
//		UserBean user = userDao.getUserByAuthtoken(authtoken, userid);
////		BookCoverBean bookBean = new BookCoverBean(  user.getUserid(),book.getBackcover().getBytes());
////		System.out.println("book :" + book.getName() + "fronstcober : " + book.getFrontcover() + book.getBackcover());
////		bookCoverRepository.updateBackcover(bookBean.getBackcover(), book.getImgid());
////		BookCoverBean bookBean = new BookCoverBean(user.getUserid() , book.getBackcover().getBytes());
////		BookCoverBean updateBook = bookCoverRepository.getById(imgdata);
////		updateBook.setBackcover(bookBean.getBackcover());
//		BookCoverBean doneBook = bookCoverRepository.findById(imgdata).get();
//		doneBook.setBackcover(book.getBackcover().getBytes());
////		BookCoverBean bookBean = new BookCoverBean(user.getUserid(), doneBook.getFrontcover() , book.getBackcover().getBytes(),imgdata);
//		bookCoverRepository.save(doneBook);
//		return ResponseEntity.ok(doneBook.getId());
//	}
//	
//	@GetMapping("/backcover/{id}")
//	public ResponseEntity<byte[]> getBackCover (@PathVariable String id) {
//		BookCoverBean file =  bookCoverRepository.findById(id).get();
//		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(file.getBackcover());
//	}
	
	
}

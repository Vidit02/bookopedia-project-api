package com.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="books")
public class BookBean {
	@Id
	@GeneratedValue
	private Integer id;
	private String bookname;
	private String author;
	private String isbn;
	private Integer price;
	private Boolean isavailable;
	private Integer userid;
	private String frontcover;
	private String backcover;
	private Integer cartid;
	public String getFrontcover() {
		return frontcover;
	}
	public void setFrontcover(String frontcover) {
		this.frontcover = frontcover;
	}
	public String getBackcover() {
		return backcover;
	}
	public void setBackcover(String backcover) {
		this.backcover = backcover;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Boolean getIsavailable() {
		return isavailable;
	}
	public void setIsavailable(Boolean isavailable) {
		this.isavailable = isavailable;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getCartid() {
		return cartid;
	}
	public void setCartid(Integer cartid) {
		this.cartid = cartid;
	}
	
	
}

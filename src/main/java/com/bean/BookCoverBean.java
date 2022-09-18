package com.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "bookcover")
public class BookCoverBean {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid" , strategy = "uuid2")
	private String id;
	private int userid;
	@Lob
	private byte[] frontcover;
	@Lob
	private byte[] backcover;
	
	public BookCoverBean(  int userid , byte[] frontcover , byte[] backcover , String id) {
		// TODO Auto-generated constructor stub
		this.frontcover = frontcover;
		this.backcover = backcover;
		this.userid = userid;
		this.id = id;
	}
	
	public BookCoverBean( byte[] frontcover , byte[] backcover ,int userid ) {
		// TODO Auto-generated constructor stub
		this.frontcover = frontcover;
		this.backcover = backcover;
		this.userid = userid;
	}
	
	public BookCoverBean( byte[] frontcover, int userid) {
		this.frontcover = frontcover;
		this.userid = userid;
	}
	
	public BookCoverBean(int userid , byte[] backcover , String id) {
		this.backcover = backcover;
		this.userid = userid;
	}
	
	public BookCoverBean() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public byte[] getFrontcover() {
		return frontcover;
	}

	public void setFrontcover(byte[] frontcover) {
		this.frontcover = frontcover;
	}

	public byte[] getBackcover() {
		return backcover;
	}

	public void setBackcover(byte[] backcover) {
		this.backcover = backcover;
	}
	
}

package com.bean;

import org.springframework.web.multipart.MultipartFile;

public class FilleBean {
	
//	private int id;
	private MultipartFile frontcover;
	private MultipartFile backcover;
	private String imgid;
	public String getImgid() {
		return imgid;
	}
	public void setImgid(String imgid) {
		this.imgid = imgid;
	}
	public MultipartFile getFrontcover() {
		return frontcover;
	}
	public void setFrontcover(MultipartFile frontcover) {
		this.frontcover = frontcover;
	}
	public MultipartFile getBackcover() {
		return backcover;
	}
	public void setBackcover(MultipartFile backcover) {
		this.backcover = backcover;
	}
	
}

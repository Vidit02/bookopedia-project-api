package com.bean;

import org.springframework.web.multipart.MultipartFile;

public class BackCoverBook {
	
	private MultipartFile backcover;
	private String imgid;
	public String getImgid() {
		return imgid;
	}
	public void setImgid(String imgid) {
		this.imgid = imgid;
	}
	public MultipartFile getBackcover() {
		return backcover;
	}
	public void setBackcover(MultipartFile backcover) {
		this.backcover = backcover;
	}
}

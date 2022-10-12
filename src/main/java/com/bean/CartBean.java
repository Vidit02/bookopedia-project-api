package com.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class CartBean {
	@Id
	@GeneratedValue
	private Integer cartid;
	private String cartitems;
	private Integer userid;
	public Integer getCartid() {
		return cartid;
	}
	public void setCartid(Integer cartid) {
		this.cartid = cartid;
	}
	public String getCartitems() {
		return cartitems;
	}
	public void setCartitems(String cartitems) {
		this.cartitems = cartitems;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
}

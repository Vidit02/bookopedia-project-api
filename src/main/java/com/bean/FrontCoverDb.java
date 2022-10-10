package com.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "frontcovers")
public class FrontCoverDb {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid" , strategy = "uuid2")
	private String id;
	private int refuser;
	@Lob
	private byte[] data;
	
	public FrontCoverDb() {
		// TODO Auto-generated constructor stub
	}
	
	public FrontCoverDb(byte[] data,int refuser) {
		this.data = data;
		this.refuser = refuser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public int getRefuser() {
		return refuser;
	}

	public void setRefuser(int refuser) {
		this.refuser = refuser;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	
}

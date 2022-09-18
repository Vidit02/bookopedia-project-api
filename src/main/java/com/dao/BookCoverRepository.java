package com.dao;

import javax.persistence.Lob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bean.BookCoverBean;

public interface BookCoverRepository extends JpaRepository<BookCoverBean, String> {
	
}
 
package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.BookBean;

public interface BookRepository extends JpaRepository<BookBean, String>{
}

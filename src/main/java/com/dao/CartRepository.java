package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.CartBean;

public interface CartRepository extends JpaRepository<CartBean,Integer> {

}

package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.OrderBean;

public interface OrderRepository extends JpaRepository<OrderBean , Integer> {

}

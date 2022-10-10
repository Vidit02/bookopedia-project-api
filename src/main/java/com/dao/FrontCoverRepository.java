package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.FrontCoverDb;

public interface FrontCoverRepository extends JpaRepository<FrontCoverDb, String> {

}

package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bean.BackCoverDb;

public interface BackCoverRepository extends JpaRepository<BackCoverDb, String> {

}

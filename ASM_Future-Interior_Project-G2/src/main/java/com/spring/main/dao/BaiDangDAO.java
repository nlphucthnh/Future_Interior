package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.BaiDang;

public interface BaiDangDAO extends JpaRepository<BaiDang, Integer> {

}

package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.HinhAnh;

public interface HinhAnhDAO extends JpaRepository<HinhAnh, String> {

}

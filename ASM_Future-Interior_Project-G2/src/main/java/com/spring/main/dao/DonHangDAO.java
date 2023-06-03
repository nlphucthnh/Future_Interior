package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.DonHang;

public interface DonHangDAO extends JpaRepository<DonHang, String>{

}

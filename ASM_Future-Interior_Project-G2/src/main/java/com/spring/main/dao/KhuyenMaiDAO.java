package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.KhuyenMai;

public interface KhuyenMaiDAO extends JpaRepository<KhuyenMai, String>{
	KhuyenMai findByIdKhuyenMai(String idKhuyenMai);
}

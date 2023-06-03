package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.SanPham;

public interface SanPhamDAO extends JpaRepository<SanPham, String>{

}

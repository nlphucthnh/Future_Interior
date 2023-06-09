package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.TaiKhoan;
import java.util.List;


public interface TaiKhoanDAO extends JpaRepository<TaiKhoan, String>{
    List<TaiKhoan> findByTenDangNhap(String tenDangNhap);
}

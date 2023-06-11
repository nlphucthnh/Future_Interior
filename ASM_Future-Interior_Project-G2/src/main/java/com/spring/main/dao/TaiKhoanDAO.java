package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.spring.main.entity.TaiKhoan;
import java.util.List;

@Repository
public interface TaiKhoanDAO extends JpaRepository<TaiKhoan, String> {
    List<TaiKhoan> findByTenDangNhap(String tenDangNhap);
}

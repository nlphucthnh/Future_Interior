package com.spring.main.dao;


import com.spring.main.entity.TaiKhoan;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TaiKhoanDAO extends JpaRepository<TaiKhoan, String>{
    List<TaiKhoan> findByTenDangNhap(String tenDangNhap);
    TaiKhoan findByEmail (String emailString);
}

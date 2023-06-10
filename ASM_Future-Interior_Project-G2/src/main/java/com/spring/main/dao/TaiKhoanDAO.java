package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.TaiKhoan;

public interface TaiKhoanDAO extends JpaRepository<TaiKhoan, String>{
    
    //kiểm tra xem tài khoản có tồn tại với tên đăng nhập đã cho hay không
    boolean existsByTenDangNhap(String tenDangNhap);
    //  trả về tài khoản dựa trên tên đăng nhập
    TaiKhoan findByTenDangNhap(String tenDangNhap); 
}

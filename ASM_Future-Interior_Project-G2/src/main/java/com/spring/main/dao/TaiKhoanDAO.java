package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.TaiKhoan;

public interface TaiKhoanDAO extends JpaRepository<TaiKhoan, String>{

}

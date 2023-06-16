package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.HinhAnh;
import java.util.List;


public interface HinhAnhDAO extends JpaRepository<HinhAnh, String> {
    HinhAnh findByTenHinhAnh(String tenHinhAnh);
}

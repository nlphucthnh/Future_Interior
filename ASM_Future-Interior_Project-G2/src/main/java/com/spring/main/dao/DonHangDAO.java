package com.spring.main.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.main.entity.DonHang;

public interface DonHangDAO extends JpaRepository<DonHang, String>{
    
}

package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.SanPhamChatLieu;



public interface SanPhamChatLieuDAO extends JpaRepository<SanPhamChatLieu, Integer> {
    SanPhamChatLieu findByIdSanPhamChatLieu(int idSanPhamChatLieu);
}

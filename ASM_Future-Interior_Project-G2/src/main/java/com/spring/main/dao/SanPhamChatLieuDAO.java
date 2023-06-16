package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.main.entity.SanPhamChatLieu;
import java.util.List;


public interface SanPhamChatLieuDAO extends JpaRepository<SanPhamChatLieu, Integer> {
    SanPhamChatLieu findByIdSanPhamChatLieu(int idSanPhamChatLieu);
}

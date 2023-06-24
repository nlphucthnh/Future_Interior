package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.NhaSanXuat;


public interface NhaSanXuatDAO extends JpaRepository<NhaSanXuat, Integer>{
	NhaSanXuat findByIdNhaSanXuat(int idNhaSanXuat);
}

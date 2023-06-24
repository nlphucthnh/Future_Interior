package com.spring.main.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.main.entity.BaiDang;


public interface BaiDangDAO extends JpaRepository<BaiDang, Integer> {
	BaiDang findByIdBaiDang(int idBaiDang);
	@Query("SELECT b FROM BaiDang b WHERE b.tieuDeBaiDang LIKE CONCAT('%', ?1, '%')")
	Page<BaiDang> findByTieuDeBaiDang(String tieuDeBaiDang,Pageable pageable);
	
	
}

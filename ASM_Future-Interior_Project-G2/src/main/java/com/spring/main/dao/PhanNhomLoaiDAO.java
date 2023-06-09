package com.spring.main.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.main.entity.PhanNhomLoai;

public interface PhanNhomLoaiDAO extends JpaRepository<PhanNhomLoai, Integer> {

    // @Query(value = "Select p from PhanNhomLoai p where p.tenPhanLoaiNhom LIKE ?1", nativeQuery = true)
    // List<PhanNhomLoai> findBYLoaiSp(String tenPhanLoaiNhom);

}

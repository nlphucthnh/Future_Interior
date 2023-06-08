package com.spring.main.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.spring.main.entity.TaiKhoan;


public interface TaiKhoanService {

	void deleteById(String id);

	Optional<TaiKhoan> findById(String ten_dang_nhap);

	List<TaiKhoan> findAll();

	<S extends TaiKhoan> S save(S entity);

	boolean existsById(String id);

	Page<TaiKhoan> findAll(Integer page, Integer limit, String field);

	String setHashMD5(String mat_khau) throws NoSuchAlgorithmException;

	String getHashMD5(String mat_khau) throws NoSuchAlgorithmException;


	
}

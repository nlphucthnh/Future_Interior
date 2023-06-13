package com.spring.main.dao;

<<<<<<< HEAD
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
>>>>>>> parent of c07f4d0 (Fix project)
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.spring.main.entity.TaiKhoan;
import java.util.List;

@Repository
public interface TaiKhoanDAO extends JpaRepository<TaiKhoan, String> {
<<<<<<< HEAD
    List<TaiKhoan> findByTenDangNhap(String tenDangNhap);
=======
	// trả về tài khoản dựa trên tên đăng nhập
	TaiKhoan findByTenDangNhap(String tenDangNhap);

	@Query("SELECT t FROM TaiKhoan t WHERE t.tenDangNhap LIKE CONCAT('%', ?1, '%') and t.vaiTro = ?2")
	Page<TaiKhoan> findByTenDangNhap(String tenDangNhap, boolean vaiTro, Pageable pageable);

	// kiểm tra xem tài khoản có tồn tại với tên đăng nhập đã cho hay không
	boolean existsByTenDangNhap(String tenDangNhap);

	

>>>>>>> parent of c07f4d0 (Fix project)
}

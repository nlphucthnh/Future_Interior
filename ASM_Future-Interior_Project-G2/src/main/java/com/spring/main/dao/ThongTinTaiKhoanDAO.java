package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.main.entity.TaiKhoan;
import com.spring.main.entity.ThongTinTaiKhoan;
import java.util.List;


public interface ThongTinTaiKhoanDAO extends JpaRepository<ThongTinTaiKhoan, Integer> {

  // @Modifying
  // @Query("UPDATE ThongTinTaiKhoan SET hoTen = :hoTen, gioiTinh = :gioiTinh,
  // soDienThoai = :soDienThoai WHERE taiKhoanTTTK.tenDangNhap LIKE
  // :taiKhoanTTTK")
  // void updateThongTinTaiKhoan(@Param("hoTen") String hoTen, @Param("gioiTinh")
  // boolean gioiTinh,
  // @Param("soDienThoai") String soDienThoai, @Param("taiKhoanTTTK") String
  // taiKhoanTTTK);

  @Modifying
  @Query("UPDATE ThongTinTaiKhoan SET hoTen = :hoTen, gioiTinh = :gioiTinh, soDienThoai = :soDienThoai WHERE taiKhoanTTTK.tenDangNhap LIKE %:taiKhoanTTTK%")
  void updateThongTinTaiKhoan(@Param("hoTen") String hoTen, @Param("gioiTinh") boolean gioiTinh,
      @Param("soDienThoai") String soDienThoai,
      @Param("taiKhoanTTTK") String taiKhoanTTTK);

  // ThongTinTaiKhoan findBytaiKhoanTTTK(String taiKhoanTTTK);

  @Modifying
  @Query("UPDATE ThongTinTaiKhoan SET anhDaiDien = :anhDaiDien WHERE taiKhoanTTTK.tenDangNhap LIKE %:taiKhoanTTTK%")
  void updateAvatar(@Param("anhDaiDien") String anhDaiDien,
      @Param("taiKhoanTTTK") String taiKhoanTTTK);

   ThongTinTaiKhoan findByTaiKhoanTTTK(TaiKhoan taiKhoanTTTK);   

}
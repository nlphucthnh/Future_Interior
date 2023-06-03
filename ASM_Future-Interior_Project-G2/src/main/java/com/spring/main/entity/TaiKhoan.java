package com.spring.main.entity;

import java.io.Serializable;
import java.util.List;

import com.spring.main.entity.TaiKhoan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tai_khoan")
public class TaiKhoan implements Serializable {
	@Id
	@Column(name = "ten_dang_nhap")
	String tenDangNhap;
	@Column(name = "mat_khau")
	String matKhau;
	@Column(name = "email")
	String email;
	@Column(name = "trang_thai")
	boolean trangThai;
	@Column(name = "vai_tro")
	boolean vaiTro;
	
	
	@OneToMany(mappedBy = "taiKhoanBaiDang")
	List<BaiDang> ListBaiDang;
	
	
//	Quan hệ 1 - 1
	@OneToOne(mappedBy = "taiKhoanTTTK")
	ThongTinTaiKhoan thongTinTaiKhoan;
	
	@OneToMany(mappedBy = "taiKhoanMuaHang")
	List<DonHang> ListDonHang;
	
	@OneToMany(mappedBy = "taiKhoanGH")
	List<GioHang> ListGioHang ;
	
}

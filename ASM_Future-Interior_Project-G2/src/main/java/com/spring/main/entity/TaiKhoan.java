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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "tai_khoan")
public class TaiKhoan implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotBlank(message= "Vui lòng nhập tài khoản!")
	@Id
	@Column(name = "ten_dang_nhap")
	String ten_dang_nhap;
	
	@NotBlank(message= "Vui lòng nhập mật khẩu!")
	@Column(name = "mat_khau")
	String mat_khau;
	@Column(name = "email")
	String email;
	@Column(name = "trang_thai")
	boolean trang_thai;
	@Column(name = "vai_tro")
	boolean vai_tro;
	
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

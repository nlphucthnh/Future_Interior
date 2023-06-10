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
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "tai_khoan")
public class TaiKhoan implements Serializable {
	private static final long serialVersionUID = 1L;
	

	//@NotBlank(message = "Vui lòng nhập tên đăng nhập!")
	//@NotNull(message= "Vui lòng nhập tên đăng nhập!")
	//@NotEmpty(message= "Vui lòng nhập tên đăng nhập!")
	@Id
	@Column(name = "ten_dang_nhap")
	String tenDangNhap;
	

//	@NotBlank(message = "Vui lòng nhập mật khẩu!1")
//	@NotNull(message= "Vui lòng nhập mật khẩu!2")
//	@NotEmpty(message= "Vui lòng nhập mật khẩu!3")
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

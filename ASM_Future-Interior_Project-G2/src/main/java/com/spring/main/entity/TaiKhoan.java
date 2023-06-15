package com.spring.main.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.main.entity.TaiKhoan;
import java.text.SimpleDateFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "tai_khoan")
public class TaiKhoan implements Serializable {
	@Id
	@Column(name = "ten_dang_nhap")
	@NotNull(message = "Không để trống tên đăng nhập")
	@NotBlank(message = "Không để trống tên đăng nhập")
	String tenDangNhap;

	@Column(name = "mat_khau")
	@NotNull(message = "Không để trống mật khẩu")
	@NotBlank(message = "Không để trống mật khẩu")
	String matKhau;
	@Column(name = "email")
	@NotNull(message = "Không để trống email")
	@NotBlank(message = "Không để trống email")
	String email;
	@Column(name = "trang_thai")
	boolean trangThai = true;
	@Column(name = "vai_tro")
	boolean vaiTro = false;
	// @Temporal(TemporalType.DATE)
	// @DateTimeFormat(pattern = "yyyy-MM-dd")
	// @Column(name = "ngay_dang_ky")
	// Date ngayDangKy = new Date();
	
	@JsonManagedReference
	@OneToMany(mappedBy = "taiKhoanBaiDang")
	List<BaiDang> ListBaiDang;
	
	
//	Quan hệ 1 - 1
	@JsonManagedReference
	@OneToOne(mappedBy = "taiKhoanTTTK")
	ThongTinTaiKhoan thongTinTaiKhoan;
	@JsonManagedReference
	@OneToMany(mappedBy = "taiKhoanMuaHang")
	List<DonHang> ListDonHang;
	@JsonManagedReference
	@OneToMany(mappedBy = "taiKhoanGH")
	List<GioHang> ListGioHang ;
	
}

package com.spring.main.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.spring.main.entity.TaiKhoan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "tai_khoan")
public class TaiKhoan implements Serializable {
	@Id
	@Column(name = "ten_dang_nhap")
	@NotBlank(message = "Vui lòng nhập tên đăng nhập")
	String tenDangNhap;
	@Column(name = "mat_khau")
	@NotBlank(message = "Vui lòng nhập mật khẩu")
	String matKhau;
	@Column(name = "email")
	@NotBlank(message = "Vui lòng nhập email")
	@Email(message = "Vui lòng nhập đúng định dạng email")
	String email;
	@Column(name = "trang_thai")
	boolean trangThai;
	@Column(name = "vai_tro")
	boolean vaiTro;
	@Temporal(TemporalType.DATE)
	Date ngayDangKy = new Date();

	@OneToMany(mappedBy = "taiKhoanBaiDang")
	List<BaiDang> ListBaiDang;

	// Quan hệ 1 - 1
	@OneToOne(mappedBy = "taiKhoanTTTK")
	ThongTinTaiKhoan thongTinTaiKhoan;

	@OneToMany(mappedBy = "taiKhoanMuaHang")
	List<DonHang> ListDonHang;

	@OneToMany(mappedBy = "taiKhoanGH")
	List<GioHang> ListGioHang;

	// @Transient
    // private String confirmPassword;

    // public String getConfirmPassword() {
    //     return confirmPassword;
    // }

    // public void setConfirmPassword(String confirmPassword) {
    //     this.confirmPassword = confirmPassword;
    // }

}

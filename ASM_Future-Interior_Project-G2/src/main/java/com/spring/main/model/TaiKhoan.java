package com.spring.main.model;

import java.util.List;

import com.spring.main.model.TaiKhoan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaiKhoan {
	String tenDangNhap;
	String matKhau;
	String email;
	boolean trangThai;
	boolean vaiTro;
}

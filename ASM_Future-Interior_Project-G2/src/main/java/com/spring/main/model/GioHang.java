package com.spring.main.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GioHang {
	int idGioHang ;
	int soLuong ;
	String idSanPham;
	String tenDangNhap;
}

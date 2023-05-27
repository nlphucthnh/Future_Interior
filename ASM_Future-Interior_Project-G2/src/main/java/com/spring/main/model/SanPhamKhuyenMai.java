package com.spring.main.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SanPhamKhuyenMai {
	String idSanPhamKhuyenMai ;
	String idKhuyenMai;
	String idSanPham;
}

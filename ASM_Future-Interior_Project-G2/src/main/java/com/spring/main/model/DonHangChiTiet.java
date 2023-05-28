package com.spring.main.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DonHangChiTiet {
	int idDonHangChiTiet; 
	int soLuong;
	String idSanPham;
	String idDonHang;
}

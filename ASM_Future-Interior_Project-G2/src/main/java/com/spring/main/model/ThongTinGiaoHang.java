package com.spring.main.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongTinGiaoHang {
	String idThongTinGiaoHang; 
	String tenNguoiMua ;
	String soDienThoai ;
	String diaChi ;
	String diaChiChiTiet;
	String idDonHang;
}

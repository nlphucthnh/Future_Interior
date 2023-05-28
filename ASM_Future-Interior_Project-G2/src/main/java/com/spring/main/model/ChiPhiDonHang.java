package com.spring.main.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiPhiDonHang {
	int idChiPhiDonHang ;
	float tongThu ;
	float phuThu ;
	float phiVanChuyen ;
	float tongCong;
	String idDonHang;
}

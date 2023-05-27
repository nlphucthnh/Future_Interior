package com.spring.main.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongTinThanhToan {
	String idThongTinThanhToan;
	String nganHang;
	String soThe;
	String tenInTrenThe;
	Date ngayPhatHanh;
	String idDonHang;
}

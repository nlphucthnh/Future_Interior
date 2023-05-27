package com.spring.main.model;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DonHang {
	String idDonHang ;
	Date ngayTao ;
	String taiKhoanMua ;
	String phuongThucThanhToan ;
	boolean trangThaiThanhToan ;
	String trangThaiDonHang;
}

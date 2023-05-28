package com.spring.main.model;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ThongTinTaiKhoan {
	String idThongTinTaiKhoan ;
	String hoTen ;
	boolean gioiTinh ;
	Date ngaySinh ;
	String soDienThoai ;
	String anhDaiDien ;
	String tenDangNhap;
}

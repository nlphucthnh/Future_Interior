package com.spring.main.model;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class KhuyenMai {
	String idKhuyenMai ;
	String tenKhuyenMai ;
	Date ngayBatDau;
	Date ngayKetThuc ;
	float phamTramKhuyenMai;
}

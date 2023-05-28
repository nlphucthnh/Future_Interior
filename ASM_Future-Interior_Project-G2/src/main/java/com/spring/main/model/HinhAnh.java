package com.spring.main.model;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HinhAnh {
	String idHinhAnh;
	String tenHinhAnh;
	Date ngayDang;
	float dungLuongAnh;
	String anhNen;
	String idSanPham;
}

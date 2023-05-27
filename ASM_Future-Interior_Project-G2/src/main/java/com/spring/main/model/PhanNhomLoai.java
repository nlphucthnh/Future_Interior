package com.spring.main.model;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PhanNhomLoai {
	int idPhanNhomLoai ;
	String tenPhanLoaiNhom ;
	Date ngayTao;
	String moTaPhanLoai;
}

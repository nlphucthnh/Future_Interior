package com.spring.main.model;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NhaSanXuat {
	int idNhaSanXuat ;
	String tenNhaSanXuat ;
	Date ngayTao;
	String moTaNhaSanXuat;
}

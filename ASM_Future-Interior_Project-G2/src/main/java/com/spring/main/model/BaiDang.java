package com.spring.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BaiDang {
	int idBaiDang;
	String tieuDeBaiDang;
	String tacGiaBaiDang;
	String moTaBaiDang;
	String noiDungBaiDang;
	String anhNen;
	String tenDangNhap;
	int luotXemBaiDang;
}

package com.spring.main.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "bai_dang")
public class BaiDang implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bai_dang")
	int idBaiDang;
	@Column(name = "tieu_de_bai_dang")
	String tieuDeBaiDang;
	@Column(name = "tac_gia_bai_dang")
	String tacGiaBaiDang;
	@Column(name = "mo_ta_bai_dang")
	String moTaBaiDang;
	@Column(name = "noi_dung_bai_dang")
	String noiDungBaiDang;
	@Column(name = "anh_nen")
	String anhNen;
	@Column(name = "luot_xem_bai_dang")
	int luotXemBaiDang;
	
	@ManyToOne
	@JoinColumn(name = "ten_dang_nhap")
	TaiKhoan taiKhoanBaiDang;
	
}

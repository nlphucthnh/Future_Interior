package com.spring.main.entity;


import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "thong_tin_giao_hang")
public class ThongTinGiaoHang implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_thong_tin_giao_hang")
	int idThongTinGiaoHang; 
	@Column(name = "ten_nguoi_mua")
	String tenNguoiMua ;
	@Column(name = "so_dien_thoai")
	String soDienThoai ;
	@Column(name = "dia_chi")
	String diaChi ;
	@Column(name = "dia_chi_chi_tiet")
	String diaChiChiTiet;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_don_hang")
	DonHang donHangTTGH;
}

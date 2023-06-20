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
import jakarta.validation.constraints.NotBlank;
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
	@NotBlank(message = "Vui lòng nhập tên người mua hàng")
	String tenNguoiMua ;

	@Column(name = "so_dien_thoai")
	@NotBlank(message = "Vui lòng nhập số điện thoại người mua hàng")
	String soDienThoai ;

	@Column(name = "thanh_pho")
	@NotBlank(message = "Vui lòng nhập thành phố hoặc tỉnh người mua hàng")
	String thanhPho;

	@Column(name = "quan_huyen")
	@NotBlank(message = "Vui lòng nhập quận hoặc huyện người mua hàng")
	String quanHuyen;

	@Column(name = "phuong_xa")
	@NotBlank(message = "Vui lòng nhập phường hoặc xã người mua hàng")
	String phuongXa;

	@Column(name = "dia_chi_chi_tiet")
	@NotBlank(message = "Vui lòng nhập tên đường và số nhà người mua hàng")
	String diaChiChiTiet;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_don_hang")
	DonHang donHangTTGH;
}

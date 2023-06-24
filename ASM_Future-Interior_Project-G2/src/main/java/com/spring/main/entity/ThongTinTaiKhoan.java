package com.spring.main.entity;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "thong_tin_tai_khoan")
public class ThongTinTaiKhoan implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_thong_tin_tai_khoan")
	int idThongTinTaiKhoan ;
	@Column(name = "ho_ten")
	String hoTen = "Nguyễn Văn A";

	@Column(name = "gioi_tinh")
	boolean gioiTinh = true;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_sinh")
	Date ngaySinh = new Date() ;
	@Column(name = "so_dien_thoai")
	String soDienThoai = "0000000001";
	@Column(name = "anh_dai_dien")
	String anhDaiDien = "https://firebasestorage.googleapis.com/v0/b/future-interior.appspot.com/o/Null.png?alt=media";
	
	
//	Quan hệ 1 - 1	
	@JsonBackReference
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ten_dang_nhap")
	TaiKhoan taiKhoanTTTK;
}

package com.spring.main.entity;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "hinh_anh")
public class HinhAnh implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id_hinh_anh")
	String idHinhAnh;
	@Column(name = "name")
	String tenHinhAnh;
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_dang")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date ngayDang = new Date();

	@Column(name = "dung_luong_anh")
	float dungLuongAnh;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "id_san_pham")
	SanPham sanPhamHA;
}

package com.spring.main.entity;
import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "hinh_anh")
public class HinhAnh implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id_hinh_anh")
	String idHinhAnh;
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_dang")
	Date ngayDang = new Date();
	@Column(name = "dung_luong_anh")
	float dungLuongAnh;
	
	
	@ManyToOne
	@JoinColumn(name = "id_san_pham")
	SanPham sanPhamHA;
}

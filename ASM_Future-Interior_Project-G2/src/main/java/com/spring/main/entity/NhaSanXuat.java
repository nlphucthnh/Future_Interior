package com.spring.main.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "nha_san_xuat")
public class NhaSanXuat implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_nha_san_xuat")
	int idNhaSanXuat ;
	@Column(name = "ten_nha_san_xuat")
	String tenNhaSanXuat ;
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_tao")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date ngayTao = new Date();
	@Column(name = "mo_ta_nha_san_xuat")
	String moTaNhaSanXuat;
	
	
	@OneToMany(mappedBy = "nhaSanXuat")
	List<SanPham> ListSanPhamNSX;
}

package com.spring.main.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "khuyen_mai")
public class KhuyenMai implements Serializable{
	@Id
	@Column(name = "id_khuyen_mai")
	String idKhuyenMai;
	@Column(name = "ten_khuyen_mai")
	String tenKhuyenMai;
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_bat_dau")
	Date ngayBatDau = new Date();
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_ket_thuc")
	Date ngayKetThuc = new Date();
	@Column(name = "pham_tram_khuyen_mai")
	float phamTramKhuyenMai;
	
	@OneToMany(mappedBy = "khuyenMaiSPKM")
	List<SanPhamKhuyenMai> ListSPKM;
}

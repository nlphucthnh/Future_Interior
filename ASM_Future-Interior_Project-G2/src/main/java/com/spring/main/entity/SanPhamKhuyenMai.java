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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "SanPhamKhuyenMai")
public class SanPhamKhuyenMai implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_san_pham_khuyen_mai")
	int idSanPhamKhuyenMai ;
	
	
	@ManyToOne
	@JoinColumn(name = "id_khuyen_mai")
	KhuyenMai khuyenMaiSPKM;
	
	
	@ManyToOne
	@JoinColumn(name = "id_san_pham")
	SanPham sanPhamSPKM;
}

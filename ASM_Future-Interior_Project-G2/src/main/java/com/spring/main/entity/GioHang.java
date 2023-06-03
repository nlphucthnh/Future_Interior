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
@Table(name = "gio_hang")
public class GioHang implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_gio_hang")
	int idGioHang ;
	@Column(name = "so_luong")
	int soLuong ;
	
	
	@ManyToOne
	@JoinColumn(name = "id_san_pham")
	SanPham sanPhamGH;
	
	@ManyToOne
	@JoinColumn(name = "ten_dang_nhap")
	TaiKhoan taiKhoanGH;
}

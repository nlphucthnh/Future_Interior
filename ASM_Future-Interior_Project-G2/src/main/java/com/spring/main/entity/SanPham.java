package com.spring.main.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "san_pham")
public class SanPham implements Serializable {
	@Id
	@Column(name = "id_san_pham")
	String idSanPham;
	@Column(name = "ten_san_pham")
	String tenSanPham;
	@Column(name = "gia_san_pham")
	float giaSanPham ;
	@Column(name = "kich_thuoc_san_pham")
	String kichThuocSanPham ;
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_tao_san_pham")
	Date ngayTaoSanPham = new Date() ;
	@Column(name = "mo_ta_san_pham")
	String moTaSanPham ;
	@Column(name = "so_luong")
	int soLuong;
	@Column(name = "khoi_luong")
	float khoiLuong;
	@Column(name = "trang_thai_ton_kho")
	boolean trangThaiTonKho;
	
	@ManyToOne
	@JoinColumn(name = "id_nha_san_xuat")
	NhaSanXuat nhaSanXuat;
	
	@ManyToOne
	@JoinColumn(name = "id_phan_nhom_loai")
	PhanNhomLoai phanNhomLoai;
	
	
	@OneToMany(mappedBy = "sanPhamHA")
	List<HinhAnh> ListHinhAnhSP;
	
	@OneToMany(mappedBy = "sanPhamDHCT")
	List<DonHangChiTiet> ListDHCT;
	
	@OneToMany(mappedBy = "sanPhamGH")
	List<GioHang> ListGioHang;
	
	@OneToMany(mappedBy = "sanPhamSPCL")
	List<SanPhamChatLieu> ListSPCL;
	
	@OneToMany(mappedBy = "sanPhamSPKM")
	List<SanPhamKhuyenMai> ListSPKM; 
}

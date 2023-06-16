package com.spring.main.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "san_pham")
public class SanPham implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id_san_pham")
	@NotBlank(message = "Vui lòng nhập mã Sản phẩm")
	String idSanPham;

	@Column(name = "ten_san_pham")
	@NotBlank(message = "Vui lòng nhập tên Sản phẩm")
	String tenSanPham;

	@Column(name = "gia_san_pham")
	@NotNull(message = "Vui lòng nhập đơn giá Sản phẩm")
	@Min(0)
	float giaSanPham;

	@Column(name = "kich_thuoc_san_pham")
	String kichThuocSanPham ;

	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_tao_san_pham")
	@NotNull(message = "Vui lòng nhập ngày tạo Sản phẩm")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date ngayTaoSanPham = new Date();

	@Column(name = "mo_ta_san_pham")
	@NotBlank(message = "Vui lòng nhập mô tả Sản phẩm")
	String moTaSanPham ;

	@Column(name = "so_luong")
	@Min(0)
	int soLuong;

	@Column(name = "khoi_luong")
	@Min(0)
	float khoiLuong;

	@Column(name = "trang_thai_ton_kho")
	boolean trangThaiTonKho = true;

	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "id_nha_san_xuat")
	NhaSanXuat nhaSanXuat;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "id_phan_nhom_loai")
	PhanNhomLoai phanNhomLoai;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "sanPhamHA")
	List<HinhAnh> ListHinhAnhSP;

	@JsonManagedReference
	@OneToMany(mappedBy = "sanPhamDHCT")
	List<DonHangChiTiet> ListDHCT;
	@JsonManagedReference
	@OneToMany(mappedBy = "sanPhamGH")
	List<GioHang> ListGioHang;
	@JsonManagedReference
	@OneToMany(mappedBy = "sanPhamSPCL")
	List<SanPhamChatLieu> ListSPCL;
	@JsonManagedReference
	@OneToMany(mappedBy = "sanPhamSPKM")
	List<SanPhamKhuyenMai> ListSPKM; 
}

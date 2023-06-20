package com.spring.main.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "don_hang")
public class DonHang implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_don_hang")
	int idDonHang ;
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_tao")
	Date ngayTao = new Date();
	
	
	@Column(name = "phuong_thuc_thanh_toan")
	boolean phuongThucThanhToan = false;
	@Column(name = "trang_thai_thanh_toan")
	boolean trangThaiThanhToan = false ;
	@Column(name = "trang_thai_don_hang")
	String trangThaiDonHang = "Đang xử lý";
	
	// @JsonBackReference
	@ManyToOne
	@JoinColumn(name = "tai_khoan_mua")
	@JsonIgnore
	TaiKhoan taiKhoanMuaHang;
	
	@OneToOne(mappedBy = "donHangCPDH")
	@JsonIgnore
	ChiPhiDonHang chiPhiDonHang;
	
	@OneToOne(mappedBy = "donHangTTGH")
	@JsonIgnore
	ThongTinGiaoHang thongTinGiaoHang;
	
	
	@OneToMany(mappedBy = "donHangDHCT")
	@JsonIgnore
	List<DonHangChiTiet> ListDHCT;
}

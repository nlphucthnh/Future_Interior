package com.spring.main.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
@Table(name = "bai_dang")
public class BaiDang implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bai_dang")
	int idBaiDang;
	
	@Column(name = "tieu_de_bai_dang")
	@NotNull(message = "Vui lòng điền tiêu đề")
	@NotBlank(message = "Tiêu đề không thể trống")
	String tieuDeBaiDang;
	
	@Column(name = "tac_gia_bai_dang")
	@NotNull(message = "Vui lòng điền tác giả")
	@NotBlank(message = "Tác giả không thể trống")
	String tacGiaBaiDang;
	
	@Column(name = "mo_ta_bai_dang")
	@NotNull(message = "Hãy viết thêm mô tả")
	@NotBlank(message = "Hãy viết thêm mô tả")
	String moTaBaiDang;
	
	@Column(name = "noi_dung_bai_dang")
	@NotNull(message = "Vui lòng nhập nội dung")
	@NotBlank(message = "Vui lòng nhập nội dung")
	String noiDungBaiDang;
	
	
	@Column(name = "anh_nen")
	String anhNen;
	
	@Column(name = "luot_xem_bai_dang")
	@Min(message = "Lươt xem lớn hoặc bằng 0", value = 0)
	int luotXemBaiDang;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_dang")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	Date ngayDang = new Date();
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "ten_dang_nhap")
	TaiKhoan taiKhoanBaiDang;
	
	public void setData(BaiDang baiDang) {
		this.anhNen = baiDang.getAnhNen() ;
		this.luotXemBaiDang = baiDang.getLuotXemBaiDang();
		this.moTaBaiDang = baiDang.getMoTaBaiDang();
		this.ngayDang = baiDang.getNgayDang();
		this.noiDungBaiDang = baiDang.getNoiDungBaiDang();
		this.tacGiaBaiDang = baiDang.getTacGiaBaiDang();
		this.taiKhoanBaiDang = baiDang.getTaiKhoanBaiDang();
		this.tieuDeBaiDang = baiDang.getTieuDeBaiDang();
	}
	
}

package com.spring.main.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "khuyen_mai")
public class KhuyenMai implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id_khuyen_mai")
	String idKhuyenMai;
	@Column(name = "ten_khuyen_mai")
	@NotBlank(message = "Vui lòng tên khuyến mãi")
	@NotNull(message = "Vui lòng tên khuyến mãi")
	String tenKhuyenMai;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "ngay_bat_dau")
	@NotNull(message = "Vui lòng ngày bắt đầu khuyến mãi")
	Date ngayBatDau = new Date();
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "ngay_ket_thuc")
	@NotNull(message = "Vui lòng ngày bắt đầu khuyến mãi")
	Date ngayKetThuc = new Date();
	

	@Column(name = "pham_tram_khuyen_mai")
	@Min(message = "Tối thiểu là 0.0 %",value = 0)
	@Max(message = "Tối đa là 100%", value =  100)
	float phamTramKhuyenMai;
	
	@OneToMany(mappedBy = "khuyenMaiSPKM")
	List<SanPhamKhuyenMai> ListSPKM;
}

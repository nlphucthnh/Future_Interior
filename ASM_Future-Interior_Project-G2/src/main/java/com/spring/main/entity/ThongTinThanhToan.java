package com.spring.main.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "thong_tin_thanh_toan")
public class ThongTinThanhToan implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "id_thong_tin_thanh_toan")
	int idThongTinThanhToan;
	@Column(name = "ngan_hang")
	String nganHang;
	@Column(name = "so_the")
	String soThe;
	@Column(name = "ten_in_tren_the")
	String tenInTrenThe;
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_phat_hanh")
	Date ngayPhatHanh = new Date();
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_don_hang")
	DonHang donHangTTTT;
	
}

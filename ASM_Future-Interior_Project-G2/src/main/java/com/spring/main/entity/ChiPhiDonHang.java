package com.spring.main.entity;
import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chi_phi_don_hang")
public class ChiPhiDonHang implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_chi_phi_don_hang")
	int idChiPhiDonHang ;
	@Column(name = "tong_phu")
	float tongPhu ;
	
	@Column(name = "phi_van_chuyen")
	float phiVanChuyen ;
	
	@Column(name = "tong_cong")
	float tongCong;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_don_hang")
	DonHang donHangCPDH;
}

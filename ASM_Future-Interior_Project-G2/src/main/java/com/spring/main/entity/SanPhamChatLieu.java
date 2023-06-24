package com.spring.main.entity;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "SanPhamChatLieu")
public class SanPhamChatLieu implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_san_pham_chat_lieu")
	int idSanPhamChatLieu;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "id_san_pham")
	SanPham sanPhamSPCL;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "id_chat_lieu")
	ChatLieu chatLieuSPCL;

}

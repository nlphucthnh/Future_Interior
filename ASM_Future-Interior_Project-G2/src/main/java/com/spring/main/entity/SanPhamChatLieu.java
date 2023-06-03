package com.spring.main.entity;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "SanPhamChatLieu")
public class SanPhamChatLieu implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_san_pham_chat_lieu")
	int idSanPhamChatLieu;
	
	@ManyToOne
	@JoinColumn(name = "id_san_pham")
	SanPham sanPhamSPCL;
	
	@ManyToOne
	@JoinColumn(name = "id_chat_lieu")
	ChatLieu chatLieuSPCL;
	
	
}

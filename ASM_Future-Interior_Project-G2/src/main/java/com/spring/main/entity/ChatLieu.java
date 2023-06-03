package com.spring.main.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "chat_lieu")
public class ChatLieu implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_chat_lieu")
	int idChatLieu ;
	@Column(name = "ten_chat_lieu")
	String tenChatLieu ;
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_tao")
	Date ngayTao = new Date() ;
	@Column(name = "mo_ta_chat_lieu")
	String moTaChatLieu;
	
	
	@OneToMany(mappedBy = "chatLieuSPCL")
	List<SanPhamChatLieu> ListSPCL;
}

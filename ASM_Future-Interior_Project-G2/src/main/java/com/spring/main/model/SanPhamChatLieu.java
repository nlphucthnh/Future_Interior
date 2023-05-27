package com.spring.main.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SanPhamChatLieu {
	String idSanPhamChatLieu;
	String idSanPham ;
	int idChatLieu;
}

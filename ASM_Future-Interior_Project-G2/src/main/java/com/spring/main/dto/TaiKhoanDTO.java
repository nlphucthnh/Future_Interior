package com.spring.main.dto;

import java.io.Serializable;


import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.spring.main.dto.TaiKhoanDTO;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotBlank(message = "Không được để trống!")
	@Length(min = 5, max = 50, message = "Phải có từ 5 - 50  ký tự!")
	private String ten_dang_nhap;

	@NotBlank(message = "Không được để trống!")
	private String mat_khau;


	private Boolean activated;

	private Boolean admin;


}

package com.spring.main.validation;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Login implements Serializable{
    
    @NotBlank(message = "Không để trống tên đăng nhập")
	String tenDangNhap;

    @NotBlank(message = "Không để trống mật khẩu")
	String matKhau;
}

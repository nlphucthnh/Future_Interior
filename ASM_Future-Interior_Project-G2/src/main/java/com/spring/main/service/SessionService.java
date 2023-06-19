package com.spring.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.main.entity.TaiKhoan;

import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {
	@Autowired
	HttpSession session;

	/**
	 * Đọc giá trị của attribute trong session
	 * 
	 * @param name tên attribute
	 * @return giá trả đọc được hoặc null nếu không tồn tại
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String name) {
		return (T) session.getAttribute(name);
	}

	/**
	 * Đọc giá trị của attribute trong session
	 * 
	 * @param name         tên attribute E
	 * @param defaultValue giá trị mặc định
	 * @return giá trả đọc được hoặc defaultValue nếu không tồn tại
	 */
	public <T> T get(String name, T defaultValue) {
		T value = get(name);
		return value != null ? value : defaultValue;
	}

	/**
	 * Thay đổi hoặc tạo mới attribute trong session
	 * 
	 * @param name  tên attribute
	 * @param value giá tri attribute
	 */
	public void set(String name, Object value) {
		session.setAttribute(name, value);
	}

	/** Xóa attribute trong session */
	public void remove(String name) {
		session.removeAttribute(name);
	}

    public TaiKhoan getSessionAttribute(String string) {
        return null;
    }

}

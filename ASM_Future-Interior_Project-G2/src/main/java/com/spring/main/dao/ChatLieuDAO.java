package com.spring.main.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.entity.ChatLieu;


public interface ChatLieuDAO extends JpaRepository<ChatLieu, Integer> {
	ChatLieu findByIdChatLieu(int idChatLieu);
}

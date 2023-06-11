package com.spring.main.service;

import com.spring.main.bean.MailInfo;

import jakarta.mail.MessagingException;

public interface MailerService {
	/**
	 * Gửi email
	 */
	void send(MailInfo mail) throws MessagingException;

	/**
	 * Gửi email đơn giản
	 */
	void send(String to, String subject, String body) throws MessagingException;


	/**
	 * Xếp mail vào hàng đợi
	 */
	//void queue(MailInfo mail);

	/**
	 * Tạo MailInfo và xếp vào hàng đợi
	 */
	//void queue(String to, String subject, String body);

}

package com.spring.main.bean;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
	
	@NotBlank(message = "Vui lòng nhập địa chỉ email của bạn!")
	@Email(message = "Vui lòng nhập đúng định dạng email!")
	String from;
	
	String to;
	
	String[] cc;
	String[] bcc;
	
	@NotBlank(message = "Vui lòng nhập họ và tên!")
	String subject;
	
	
	String body;
	 
	
	String[] attachments;

	public MailInfo(String to, String subject, String body) {
		super();
		this.from = "Future Interior <lehoangthao2410@gmail.com>";
		this.to = to;
		this.subject = subject;
		this.body = body;
		
	}
}

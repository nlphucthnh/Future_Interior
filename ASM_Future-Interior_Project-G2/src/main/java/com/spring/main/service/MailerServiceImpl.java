package com.spring.main.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import com.spring.main.entity.MailInfo;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;


@Service
public class MailerServiceImpl implements MailerService{
     @Autowired
  JavaMailSender sender;
  List<MailInfo> list = new ArrayList<>();

  @Override
  public void send(MailInfo mail) throws MessagingException {

    MimeMessage message = sender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
    helper.setFrom(mail.getFrom());
    helper.setTo(mail.getTo());
    helper.setSubject(mail.getSubject());
    helper.setText(mail.getBody(), true);
    helper.setReplyTo(mail.getFrom());
    String[] cc = mail.getCc();
    if (cc != null && cc.length > 0) {
      helper.setCc(cc);
    }
    String[] bcc = mail.getBcc();
    if (bcc != null && bcc.length > 0) {
      helper.setBcc(bcc);
    }
    String[] attachments = mail.getAttachments();
    if (attachments != null && attachments.length > 0) {
      for (String path : attachments) {
        File file = new File(path);
        helper.addAttachment(file.getName(), file);
      }
    }
    sender.send(message);
  }

  @Override
  public void send(String to, String subject, String body)
      throws MessagingException {
    this.send(new MailInfo(to, subject, body));
  }

  @Override
  public void queue(MailInfo mail) {
    list.add(mail);
  }

  @Override
  public void queue(String to, String subject, String body) {
    queue(new MailInfo(to, subject, body));
  }

  @Scheduled(fixedDelay = 3000)
  public void run() {
    while (!list.isEmpty()) {
      MailInfo mail = list.remove(0);
      try {
        this.send(mail);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}

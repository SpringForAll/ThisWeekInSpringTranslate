package com.spring4all.service.impl;

import com.spring4all.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.sender}")
    private String sender;

    @Value("${email.receiver}")
    private String receivers;

    /**
     * 发送文本邮件
     *
     * @param subject  邮件主题
     * @param text     邮件主体内容
     * @param receiver
     */
    @Override
    public void sendText(String subject, String text, String... receiver) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(receiver);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);

    }


    @Override
    public void sendTextAndFile(String subject, String text, String fileName, String fileContent, String... receiver) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            if (null == receiver || receiver.length <= 0) {
                if (null != receivers && receivers.trim().length() > 0) {
                    receiver = receivers.split(",");
                }
            }
            mimeMessageHelper.setTo(receiver);
            mimeMessageHelper.addAttachment(fileName, new ByteArrayResource(fileContent.getBytes()));
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text);
        } catch (MessagingException e) {
            logger.error("errorMessage:{}", e);
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);
    }
}
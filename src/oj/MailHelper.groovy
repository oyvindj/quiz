package oj

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

import javax.mail.MessagingException
import javax.mail.internet.MimeMessage
import org.springframework.stereotype.Service

@Component
class MailHelper {
    final mailSender

    @Autowired
    MailHelper(JavaMailSender mailSender) {
        this.mailSender = mailSender
    }   
    
    void sendmail(String to, String subject, String content) {
        def mail = mailSender.createMimeMessage()
        def helper = new MimeMessageHelper(mail, true)
        helper.setTo to
        helper.setFrom "oyvindlj@gmail.com"
        helper.setSubject subject
        helper.setText content
        mailSender.send(mail)
    }
}

package oj

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.mail.Message

@Component
class MailUtil {
    @Autowired
    MailAddressDB mailAddressDB

    @Autowired
    MailHelper mailHelper

    @Autowired
    MailReader mailReader

    List<Message> messages

    void read() {
        messages = mailReader.readMail()
        def count = 1
        for(Message msg : messages) {
            mailReader.readMessageInfo(msg, count++)
        }
    }
    void readMesssage(int id) {
        def msg = messages.get(id - 1)
        mailReader.readMessage(msg)
    }

    void send(String nick, String subject, String content) {
        def ma = mailAddressDB.findByNick(nick)
        mailHelper.sendmail(ma.address, subject, content)
    }

    void addMailAddress(String address, String nick) {
        def ma = new MailAddress()
        ma.address = address
        ma.nick = nick
        mailAddressDB.save(ma)
    }
    List<MailAddress> getMailAddresses() {
        return mailAddressDB.findAll()
    }
}

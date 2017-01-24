package oj

import org.springframework.stereotype.Component

import javax.mail.Address
import javax.mail.BodyPart
import javax.mail.Folder
import javax.mail.Message
import javax.mail.Multipart
import javax.mail.Session
import javax.mail.Store

@Component
class MailReader {

    List<Message> readMail() {
        def messages = new ArrayList<>()
        def props = new Properties()
        props.setProperty("mail.store.protocol", "imaps")

        def session = Session.getInstance(props, null)
        def store = session.getStore()
        store.connect("imap.gmail.com", "oyvindlj@gmail.com", "google123qwe")
        def inbox = store.getFolder("INBOX")
        inbox.open(Folder.READ_ONLY)
        def count = inbox.getMessageCount()
        def readCount = 5
        if(count < readCount) {
            readCount = count
        }
        println "Found " + count + " messages in Inbox"
        for(int i = 0; i < readCount; i++) {
            def id = count - i
            Message msg = inbox.getMessage(id)
            messages.add(msg)
        }
        return messages
    }

    void readMessageInfo(Message msg, Integer id) {
        def inmsg = msg.getFrom()
        for (Address address : inmsg) {
            println id + " - FROM:" + address.toString()
        }
        println "SENT DATE:" + msg.getSentDate()
        println "SUBJECT:" + msg.getSubject()
        println ""
    }

    void readMessage(Message msg) {
        try {
            Multipart mp = (Multipart) msg.getContent()
            BodyPart bp = mp.getBodyPart(0)
            println "CONTENT:" + bp.getContent()
        } catch (Exception e) {
            println "Failed to read message content!"
        }
    }
}

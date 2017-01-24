package oj

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MailCLI {
    @Autowired
    MailUtil mailUtil

    void runCommand(String arg, Scanner scanner) {
        switch(arg) {
            case "create":
                println "nick: "
                def nick = scanner.nextLine()
                println "subject: "
                def subject = scanner.nextLine()
                println "content: "
                def conent = scanner.nextLine()
                println "sending email to " + nick + " with subject " + subject
                mailUtil.send(nick, subject, conent)
                break
            case "add":
                println "address: "
                def address = scanner.nextLine()
                println "nick: "
                def nick = scanner.nextLine()
                mailUtil.addMailAddress(address, nick)
                println "added address " + address + " to DB"
                break
            case "list":
                def list = mailUtil.getMailAddresses()
                for(MailAddress ma : list) {
                    println ma.nick + " - " + ma.address
                }
                break
            case "read":
                mailUtil.read()
                break
            case "readmsg":
                println "id: "
                def id = scanner.nextInt()
                mailUtil.readMesssage(id)
                break
            default:
                println "unknown mail arg: [" + arg + "]"
                break
        }
    }
}

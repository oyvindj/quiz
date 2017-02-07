package oj

import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/")
class WebService {
    @Autowired
    DB db
    @Autowired
    MailHelper mailSender
    @Autowired
    ServerWSClient wsClient
    @Autowired
    Config config
    @Autowired
    QuizManager quizManager

    @Autowired
    ServerSession ss

    WebService() {
    }

    @RequestMapping(method = RequestMethod.GET, value = "login")
    String login(@RequestParam("nick") String nick, HttpServletRequest req) {
        def login = new Login()
        login.nick = nick
        login.time = new Date()
        login.host = req.getRemoteAddr()
        ss.logins.add(login)
        println nick + " logged in from " + req.getRemoteAddr()
        return "success"
    }
    @RequestMapping(method = RequestMethod.GET, value = "ping")
    String ping() {
        println "got pinged at: " + new Date().toString()
        return "ping reply: " + new Date().toString()
    }
    @RequestMapping(method = RequestMethod.GET, value = "quizes")
    String quizes() {
        def sb = new StringBuffer()
        sb.append "Quizes: \n"
        def count = 1
        for(Quiz q : ss.quizes) {
            sb.append("(" + count++ + ") - ")
            sb.append(q.nick1 + " : " + q.nick2)
            sb.append("\n")
        }
        return sb.toString()
    }

    @RequestMapping(method = RequestMethod.GET, value = "logins")
    String logins() {
        def sb = new StringBuffer()
        def count = 1
        for(Login l : ss.logins) {
            sb.append("(" + count++ + ") - ")
            sb.append(l.nick)
            sb.append("\n")
        }
        return sb.toString()
    }

    @RequestMapping(method = RequestMethod.GET, value = "challenge")
    Boolean challenge(
        @RequestParam("nick") nick,
        @RequestParam("challengedNick") challengedNick) {
        def login = ss.getLogin(challengedNick)
        def answer = wsClient.challenge(login.host, nick)
        if(answer) {
            println nick + "'s challenge was accepted by " + challengedNick
            quizManager.startQuiz(nick, challengedNick)
        } else {
            println nick + "'s challenge was not accepted by " + challengedNick

        }
        return answer
    }
    @RequestMapping(method = RequestMethod.GET, value = "pingLogin")
    String pingLogin(@RequestParam("nick") String nick) {
        println "pinging " + nick
        return wsClient.pingLogin(nick)
    }


    @RequestMapping(method = RequestMethod.GET, value = "create")
    String createPerson(@RequestParam("name") String name) {
        def person = new Person(name)
        person = db.save(person)
        return "created person with name " + person.name + " and id: " + person.id
    }

    @RequestMapping(method = RequestMethod.GET, value = "hello")
    String hello () {
        return config.foo
    }
    @RequestMapping(method = RequestMethod.GET, value = "sendmail")
    String sendmail () {
        def to = "oyvindj@me.com"
        def subject = "Foo Subject!!"
        def text = "Some random content for the email..."
        mailSender.sendmail(to, subject, text)
        return "successfully sent mail..."
    }

    @RequestMapping(method = RequestMethod.GET, value = "callws")
    String callws () {
        return wsClient.fooService("foo input...")
    }
    @RequestMapping(method = RequestMethod.GET, value = "callws2")
    String callws2 () {
        //def url = "http://api.met.no/weatherapi/dummy/0.3/?string=schmoo;number=1"
        //def url = "http://api.met.no/weatherapi/errornotifications/1.0/"
        def url = "http://api.dogu.no/api/Nrk/GetLatestNews/json"
        return wsClient.nrkService(url)
    }

    @RequestMapping(method = RequestMethod.GET, value = "list")
    def String list () {
        def names = ""
        List<Person> persons = db.findAll()
        for(Person person : persons) {
            System.out.println("person: " + person)
            names = names + person.name + ", "
        }
        return names
    }
}

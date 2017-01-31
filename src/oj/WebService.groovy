package oj

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class WebService {
    @Autowired
    DB db
    @Autowired
    MailHelper mailSender
    @Autowired
    WSClient wsClient
    @Autowired
    Config config

    List<Login> logins
    List<Quiz> quizes

    @RequestMapping(method = RequestMethod.GET, value = "login")
    String login(@RequestParam("nick") String nick) {
        def login = new Login()
        login.nick = nick
        login.time = new Date()
        logins.add(login)
        return "success"
    }

    @RequestMapping(method = RequestMethod.GET, value = "logins")
    List<Login> logins() {
        return logins
    }

    @RequestMapping(method = RequestMethod.GET, value = "challenge")
    Boolean challenge(
        @RequestParam("nick") nick,
        @RequestParam("challengedNick") challengedNick) {
        def answer = wsClient.challenge(nick)
        if(answer) {
            println nick + "'s challenge was accepted by " + challengedNick
            def quiz = new Quiz()
            quiz.nick1 = nick
            quiz.nick2 = challengedNick
            quiz.time = new Date()
            quizes.add(quiz)
        } else {
            println nick + "'s challenge was not accepted by " + challengedNick

        }
        return answer
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

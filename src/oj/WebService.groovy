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

    @RequestMapping(method = RequestMethod.GET, value = "create")
    def String createPerson(@RequestParam("name") String name) {
        def person = new Person(name)
        person = db.save(person)
        return "created person with name " + person.name + " and id: " + person.id
    }

    @RequestMapping(method = RequestMethod.GET, value = "hello")
    def String hello () {
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

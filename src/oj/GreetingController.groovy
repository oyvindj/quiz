package oj

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller

@Controller
class GreetingController {
    @Autowired
    ServerSession ss

    @Autowired
    WebService ws

    @Autowired
    SimpMessageSendingOperations messagingTemplate

    //@MessageMapping("/logins")
    //@SendTo("/topic/logins")
    //public String logins() throws Exception {
    //    return ws.logins()
    //}

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        def login = new Login()
        login.nick = message.name
        login.time = new Date()
        ss.logins.add(login)
        messagingTemplate.convertAndSend("/topic/logins", ss.logins)
        //messagingTemplate.convertAndSend("/topic/logins", ws.logins())
        return new Greeting("Hello, " + message.getName() + "!")
        //return new Greeting("Logins: " + ws.logins())
    }

}

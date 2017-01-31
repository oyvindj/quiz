package oj

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/client/")
class ClientController {

    @RequestMapping(method = RequestMethod.GET, value = "challenge")
    def Boolean challenge(@RequestParam("nick") String nick) {
        def scanner = new Scanner(System.in)
        println "You are challenged by " + nick + ". Do you accept?"
        return Boolean.parseBoolean(scanner.nextLine())
    }
}

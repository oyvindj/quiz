package oj

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/client/")
class ClientController {
    @Autowired
    ClientSession cs

    @RequestMapping(method = RequestMethod.GET, value = "message")
    def void message(@RequestParam("message") String msg) {
        println msg
    }

    @RequestMapping(method = RequestMethod.GET, value = "countdown")
    def void message(@RequestParam("ticks") Integer ticks) {
        for(int i = 0; i < ticks; i++) {
            print(".")
            System.out.print("\007")
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "challenge")
    def Boolean challenge(@RequestParam("nick") String nick) {
        println "You are challenged by " + nick + ". Do you accept? (Y/N)"
        print "> "
        def valid = false
        def input = "undefined"
        while(!valid) {
            try {
                input = cs.scanner.nextLine().toLowerCase()
            } catch(Exception e) { }
            if((input == 'y') || (input == 'n')) {
                valid = true
            }
        }
        return input == "y"
    }
}

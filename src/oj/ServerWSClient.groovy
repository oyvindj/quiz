package oj

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ServerWSClient {
    @Autowired
    ServerSession ss

    // Client Call!!
    Boolean challenge(String host, String nick) {
        def restTemplate = new RestTemplate()
        def url = "http://" + host + ":8080/client/challenge?nick=" + nick
        return restTemplate.getForObject(url, Boolean.class)
    }

    void countdown(String host, Integer ticks) {
        def restTemplate = new RestTemplate()
        def url = "http://" + host + ":8080/client/countdown?ticks=" + ticks
        restTemplate.getForObject(url, String.class)
    }
    void message(String host, String msg) {
        def restTemplate = new RestTemplate()
        def url = "http://" + host + ":8080/client/countdown?message=" + msg
        restTemplate.getForObject(url, String.class)
    }

    String fooService(String text) {
        def restTemplate = new RestTemplate()
        def url = "http://gturnquist-quoters.cfapps.io/api/random"
        def result = restTemplate.getForObject(url, Quote.class)
        return result.value.quote
    }

    News nrkService() {
        def url = "http://api.dogu.no/api/Nrk/GetLatestNews/json?count=10"
        def restTemplate = new RestTemplate()
        def news = restTemplate.getForObject(url, News.class)
        return news
    }
    String pingLogin(String nick) {
        println "pinging login with nick: " + nick
        def host = ss.getHost(nick)
        def restTemplate = new RestTemplate()
        def url = "http://" + host + ":8080/ping"
        println "pingin " + nick + " at " +  host
        def result = restTemplate.getForObject(url, String.class)
        println "got reply: " + result
        return result
    }
}

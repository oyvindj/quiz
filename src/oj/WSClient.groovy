package oj

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.RestTemplate

@Component
@Slf4j
class WSClient {
    Config config

    @Autowired
    WebService webService

    @Autowired
    WSClient(Config config) {
        this.config = config
    }

    String ping(String nick) {
        def restTemplate = new RestTemplate()
        def url = "http://" + config.quizServer + ":8080/pingLogin?nick=" + nick
        return restTemplate.getForObject(url, String.class)
    }






    Boolean challengeOpponent(String nick, String opponentNick) {
        def restTemplate = new RestTemplate()
        def url = "http://" + config.quizServer + ":8080/challenge?nick=" + nick + "&" + "challengedNick=" + opponentNick
        return restTemplate.getForObject(url, Boolean.class)
    }
    String getLogins() {
        def restTemplate = new RestTemplate()
        def url = "http://" + config.quizServer + ":8080/logins"
        return restTemplate.getForObject(url, String.class)
    }
    String getQuizes() {
        def restTemplate = new RestTemplate()
        def url = "http://" + config.quizServer + ":8080/quizes"
        return restTemplate.getForObject(url, String.class)
    }
    Boolean login(String nick) {
        def restTemplate = new RestTemplate()
        def url = "http://" + config.quizServer + ":8080/login?nick=" + nick
        restTemplate.getForObject(url, String.class)
        return true
    }

    String stringService(String url, String text) {
        def restTemplate = new RestTemplate()
        def result = restTemplate.getForObject(url, String.class)
        return result
    }

    static String getTitles(News news) {
        def sb = new StringBuffer()
        def count = 1
        for(NewsItem item : news.newsItems) {
            sb.append("(" + count++ + ") ")
            sb.append(item.title)
            sb.append("\n")
        }
        return sb.toString()
    }
}

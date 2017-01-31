package oj

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
@Slf4j
class WSClient {
    Config config
    @Autowired
    WSClient(Config config) {
        this.config = config
    }

    String challenge(String nick) {
        def restTemplate = new RestTemplate()
        def url = "http://localhost:8080/client/challenge?nick=" + nick
        return restTemplate.getForObject(url, Boolean.class)
    }
    String challengeOpponent(String nick, String opponentNick) {
        def restTemplate = new RestTemplate()
        def url = "http://localhost:8080/challenge?nick=" + nick + "&" + "challengedNick=" + opponentNick
        return restTemplate.getForObject(url, Boolean.class)
    }
    List<Login> getLogins() {
        def restTemplate = new RestTemplate()
        def url = "htto://localhost:8080/logins"
        return restTemplate.exchange(
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Login>>(){}).getBody();
        //return restTemplate.getForObject(url, Login.class)
    }
    Boolean login(String nick) {
        def restTemplate = new RestTemplate()
        def url = "htto://" + config.quizServer + ":8080/login?nick=" + nick
        restTemplate.getForObject(url, String.class)
        return true
    }
    String fooService(String text) {
        def restTemplate = new RestTemplate()
        def url = "http://gturnquist-quoters.cfapps.io/api/random"
        def result = restTemplate.getForObject(url, Quote.class)
        return result.value.quote
    }

    String stringService(String url, String text) {
        def restTemplate = new RestTemplate()
        def result = restTemplate.getForObject(url, String.class)
        return result
    }
    News nrkService() {
        def url = "http://api.dogu.no/api/Nrk/GetLatestNews/json?count=10"
        def restTemplate = new RestTemplate()
        def news = restTemplate.getForObject(url, News.class)
        return news
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

package oj

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
@Slf4j
class WSClient {
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

package oj

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NewsCLI {
    @Autowired
    WSClient wsClient

    News news

    void runCommand(String arg, Scanner scanner) {
        if(news == null) {
            news = wsClient.nrkService()
        }
        if(arg == null) {
            println WSClient.getTitles(news)
        } else {
            def id = Integer.parseInt(arg) - 1
            println news.newsItems.get(id).description
        }
    }
}

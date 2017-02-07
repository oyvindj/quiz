package oj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuizManager {
    @Autowired
    ServerSession ss

    @Autowired
    ServerWSClient wsClient

    void startQuiz(String nick1, String nick2) {
        def quiz = createQuiz(nick1, nick2)
        ss.quizes.add(quiz)
        println "server starting quiz..."
        wsClient.message(ss.getHost(nick1), "Starting Quiz in 10 Seconds!")
        wsClient.countdown(ss.getHost(nick1), 10)
        wsClient.message(ss.getHost(nick2), "Starting Quiz in 10 Seconds!")
        wsClient.countdown(ss.getHost(nick2), 10)
        println "server started quiz..."


    }

    Quiz createQuiz(String nick1, String nick2) {
        def quiz = new Quiz()
        quiz.nick1 = nick1
        quiz.nick2 = nick2
        quiz.host1 = ss.getLogin(nick1).host
        quiz.host2 = ss.getLogin(nick2).host
        quiz.time = new Date()
        return quiz
    }
}

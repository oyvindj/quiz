package oj

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class QuizCLI {
    @Autowired
    MailUtil mailUtil

    @Autowired
    QuestionDB questionDB

    @Autowired
    WSClient wsClient

    void start(Scanner scanner) {
        // TODO save to user properties instead
        def file = new File(".quizrc")
        def nick = "undefined"
        if(!file.exists()) {
            println "nick: "
            nick = scanner.nextLine()
            file << nick
        } else {
            nick = new File(".quizrc").text
        }
        println "Welcome to Quiz " + nick
    }

    String getNick() {
        return new File(".quizrc").text
    }
    void nick(Scanner scanner) {
        println new File(".quizrc").text
    }

    void runCommand(String cmd, String arg, Scanner scanner) {
        switch(cmd) {
            case "login":
                wsClient.login(arg)
            case "test":
                def qs = questionDB.findAll()
                def index = getRandom(qs.size())
                def q = qs.get(index)
                long start = new Date().time
                println q.question
                println "1: " + q.answer1
                println "2: " + q.answer2
                println "3: " + q.answer3
                def answer = getInput("Your answer: ", scanner)
                long end = new Date().time
                def time = end - start
                if(Integer.parseInt(answer) == q.correctAnswer) {
                    println "Correct answer in " + time + " ms!!!"
                } else {
                    println "Wrong answer in " + time + "ms..."
                }
                break
            case "addq":
                def q = getInput("question", scanner)
                def a1 = getInput("answer 1", scanner)
                def a2 = getInput("answer 2", scanner)
                def a3 = getInput("answer 3", scanner)
                def answer = getInput("answer", scanner)
                def question = new Question()
                question.question = q
                question.answer1 = a1
                question.answer2 = a2
                question.answer3 = a3
                question.correctAnswer = Integer.parseInt(answer)
                question.author = getNick()
                question.date = new Date()
                questionDB.save(question)
                break
            default:
                println "unknown quiz arg: [" + arg + "]"
                break
        }
    }

    Integer getRandom(Integer max) {
        def random = new Random()
        return random.nextInt(max)
    }
    String getInput(String prompt, Scanner scanner) {
        println prompt + ": "
        return scanner.nextLine()
    }
}

package oj

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CLI implements CommandLineRunner{
    @Autowired
    QuizCLI quizCLI

    @Autowired
    WSClient wsClient

    @Autowired
    ClientSession cs

    @Override
    void run(String... args) throws Exception {
        println ""
        println "===== QUIZ APP ====="
        //def scanner = new Scanner(System.in)
        def scanner = cs.scanner
        def quit = false
        quizCLI.start(scanner)
        while(!quit) {
            print "> "
            def line = null
            try {
                line = scanner.nextLine()
            } catch (Exception e) {}
            def st = new StringTokenizer(line, " ")
            def cmd = null
            if(st.hasMoreElements()) {
                cmd = st.nextElement()
            }
            def arg = null
            if(st.hasMoreElements()) {
                arg = st.nextElement()
            }
            if(cmd != null) {
                quit = runCommand(cmd, arg, scanner)
            } else {
                println ""
            }
        }
        System.exit(-1)
    }

    boolean runCommand(String cmd, String arg, Scanner scanner) {
        switch (cmd) {
            case "challenge":
                def answer = wsClient.challengeOpponent("oyvind", arg)
                if(answer) {
                    println "Your challenge was accepted by " + arg
                } else {
                    println "Your challenge was not accepted by " + arg
                }
                break
            case "ping":
                def result = wsClient.ping(arg)
                println "got result: " + result
                break
            case "quizes":
                def result = wsClient.getQuizes()
                println result
                break
            case "logins":
                def result = wsClient.getLogins()
                println result
                break
            case "login":
                quizCLI.runCommand(cmd, arg, scanner)
                break
            case "start":
                quizCLI.start(scanner)
                break
            case "addq":
                quizCLI.runCommand(cmd, arg, scanner)
                break
            case "test":
                quizCLI.runCommand(cmd, arg, scanner)
                break
            case "nick":
                quizCLI.nick(scanner)
                break
            case "q":
                return true
            default:
                println "unknown command!"
                break
        }
        return false
    }
}
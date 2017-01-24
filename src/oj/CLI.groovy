package oj

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CLI implements CommandLineRunner{
    @Autowired
    QuizCLI quizCLI

    @Override
    void run(String... args) throws Exception {
        println ""
        println "===== QUIZ APP ====="
        def scanner = new Scanner(System.in)
        def quit = false
        quizCLI.start(scanner)
        while(!quit) {
            print "> "
            def line = scanner.nextLine()
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
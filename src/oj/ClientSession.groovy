package oj

import org.springframework.stereotype.Component

@Component
class ClientSession {
    static Scanner scanner

    ClientSession() {
        scanner = new Scanner(System.in)
    }
}

package oj

import org.springframework.stereotype.Component

@Component
class ServerSession {
    List<Quiz> quizes
    List<Login> logins

    ServerSession() {
        quizes = new ArrayList<>()
        logins = new ArrayList<>()
    }

    Login getLogin(String nick) {
        return logins.stream().filter { y -> y.nick == nick }.findFirst().get()

        /*def login = null
        for(Login l : logins) {
            if(nick == l.nick) {
                login = l
            }
        }
        return login*/
    }
    String getHost(String nick) {
        return getLogin(nick).host
    }
}

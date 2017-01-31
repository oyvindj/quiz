package oj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${oj.foo}")
    public String foo = "default";
    @Value("${quiz.server}")
    public String quizServer = "default";
}
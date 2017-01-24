package oj

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
interface DB extends MongoRepository<Person, String> {
}
@Component
interface QuestionDB extends MongoRepository<Question, String> {
}
@Component
interface MailAddressDB extends MongoRepository<MailAddress, String> {
    MailAddress findByNick(String nick)
}

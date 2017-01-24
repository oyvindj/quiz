package oj

import groovy.transform.Immutable
import org.springframework.data.annotation.Id

//@Immutable
class Person {
  @Id String id
  String name
  public Person(String name) {
    this.name = name
  }
}

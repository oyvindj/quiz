package oj

import groovy.transform.ToString
@ToString
class News {
    String link
    String title
    String description
    List<NewsItem> newsItems
}
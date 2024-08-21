package com.sbs.java.board.article;

public class Article {
  int id;
  String subject;
  String content;

  Article(int id, String subject, String content) {
    this.id = id;
    this.subject = subject;
    this.content = content;
  }

  @Override // 어노테이션
  public String toString() { // 메서드 오버라이딩
    return "{id : %d, subject: \"%s\", content : \"%s\"}".formatted(id, subject, content);
  }
}

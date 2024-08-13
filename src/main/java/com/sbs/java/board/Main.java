package com.sbs.java.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    int lastArticleId = 0;
    Article lastArticle = null;

    List<Article> articles = new ArrayList<>();

    // 테스트 게시물
    articles.add(new Article(1, "제목1", "내용1"));
    articles.add(new Article(2, "제목2", "내용2"));
    articles.add(new Article(3, "제목3", "내용3"));

    Scanner sc = new Scanner(System.in);
    System.out.println("== 자바 텍스트 게시판 ==");

    while (true) {
      System.out.print("명령) ");
      String cmd = sc.nextLine();

      if (cmd.equals("/usr/article/write")) {
        System.out.println("== 게시물 작성 ==");
        System.out.print("제목 : ");
        String subject = sc.nextLine();

        System.out.print("내용 : ");
        String content = sc.nextLine();

        int id = ++lastArticleId;

        Article article = new Article(id, subject, content); // 게시물 객체 생성
        lastArticle = article;

        System.out.println("생성 된 게시물 객체 : " + article);
        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
      } else if (cmd.equals("/usr/article/detail")) {
        Article article = lastArticle;

        if(article == null) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        System.out.println("== 게시물 상세보기 ==");
        System.out.printf("번호 : %d\n", article.id);
        System.out.printf("제목 : %s\n", article.subject);
        System.out.printf("내용 : %s\n", article.content);

      } else if (cmd.equals("/usr/article/list")) {
        if(articles.size() == 0) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        System.out.println("== 게시물 리스트 ==");
        System.out.println("-------------------");
        System.out.println("|  번호  |  제목  |");
        System.out.println("-------------------");

        for(int i = 0; i < articles.size(); i++) {
          Article article = articles.get(i);
          System.out.printf("|   %d    |  %s  |\n", article.id, article.subject);
        }

      } else if (cmd.equals("exit")) {
        break;
      } else {
        System.out.println("잘못 된 명령어입니다.");
      }
    }

    System.out.println("== 자바 텍스트 게시판 종료 ==");
    sc.close();
  }
}

class Article {
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
package com.sbs.java.board;

import java.util.*;

public class Main {
  static void makeTestData(List<Article> articles) {
    articles.add(new Article(1, "제목1", "내용1"));
    articles.add(new Article(2, "제목2", "내용2"));
    articles.add(new Article(3, "제목3", "내용3"));
  }

  public static void main(String[] args) {
    int lastArticleId = 0;
    List<Article> articles = new ArrayList<>();

    makeTestData(articles);

    if (!articles.isEmpty()) {
      lastArticleId = articles.get(articles.size() - 1).id;
    }

    Scanner sc = new Scanner(System.in);
    System.out.println("== 자바 텍스트 게시판 ==");

    while (true) {
      System.out.print("명령) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);

      if (rq.getUrlPath().equals("/usr/article/write")) {
        System.out.println("== 게시물 작성 ==");
        System.out.print("제목 : ");
        String subject = sc.nextLine();

        System.out.print("내용 : ");
        String content = sc.nextLine();

        int id = ++lastArticleId;

        Article article = new Article(id, subject, content); // 게시물 객체 생성

        articles.add(article);

        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
      } else if (rq.getUrlPath().equals("/usr/article/detail")) {
        Map<String, String> params = rq.getParams();
        int id = 0;

        try {
          id = Integer.parseInt(params.get("id"));
        } catch (NumberFormatException e) {
          System.out.println("id를 정수 형태로 입력해주세요.");
          continue;
        }

        if (articles.isEmpty()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        if (id > articles.size()) {
          System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
          continue;
        }

        Article article = articles.get(id - 1);

        System.out.println("== 게시물 상세보기 ==");
        System.out.printf("번호 : %d\n", article.id);
        System.out.printf("제목 : %s\n", article.subject);
        System.out.printf("내용 : %s\n", article.content);

      } else if (rq.getUrlPath().equals("/usr/article/list")) {
        Map<String, String> params = rq.getParams();

        if (articles.isEmpty()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        boolean orderByIdDesc = true;

        if (params.containsKey("orderBy") && params.get("orderBy").equals("idAsc")) {
          orderByIdDesc = false;
        }

        // 검색 시작
        List<Article> filteredArticles = articles;

        if(params.containsKey("searchKeyword")) {
          String searchKeyword = params.get("searchKeyword");

          filteredArticles = new ArrayList<>();

          for(Article article : articles) {
            if(article.subject.contains(searchKeyword) || article.content.contains(searchKeyword)) {
              filteredArticles.add(article);
            }
          }
        }
        // 검색 끝

        List<Article> soredArticles = filteredArticles;
        // List<Article> soredArticles = articles;

        System.out.println("== 게시물 리스트 ==");
        System.out.println("-------------------");
        System.out.println("|  번호  |  제목  |");
        System.out.println("-------------------");

        if(orderByIdDesc) { // idAsc(오름차순)가 없으면 기본값인 idDesc(내림차순)
          for (int i = soredArticles.size() - 1; i >= 0; i--) {
            Article article = soredArticles.get(i);
            System.out.printf("|   %d    |  %s  |\n", article.id, article.subject);
          }
        }
        else {
          for(Article article : soredArticles) {
            System.out.printf("|   %d    |  %s  |\n", article.id, article.subject);
          }
        }

      } else if (rq.getUrlPath().equals("exit")) {
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

class Rq {
  String url;
  Map<String, String> params;
  String urlPath;

  Rq(String url) {
    this.url = url;
    this.params = Util.getParamsFromUrl(url);
    this.urlPath = Util.getUrlPathFromUrl(url);
  }

  public Map<String, String> getParams() {
    return params;
  }

  public String getUrlPath() {
    return urlPath;
  }
}

class Util {
  static Map<String, String> getParamsFromUrl(String url) {
    Map<String, String> params = new LinkedHashMap<>();
    String[] urlBits = url.split("\\?", 2);

    if (urlBits.length == 1) {
      return params;
    }

    String queryStr = urlBits[1];
    for (String bit : queryStr.split("&")) {
      String[] bits = bit.split("=", 2);

      if (bits.length == 1) {
        continue;
      }

      params.put(bits[0], bits[1]);
    }

    return params;
  }

  static String getUrlPathFromUrl(String url) {
    return url.split("\\?", 2)[0];
  }
}

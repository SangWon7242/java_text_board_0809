package com.sbs.java.board;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
  static void makeTestData(List<Article> articles) {
    IntStream.rangeClosed(1, 100)
        .forEach(i -> articles.add(new Article(i, "제목" + i, "내용" + i)));
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
        actionUsrArticleWrite(sc, articles, lastArticleId);
        lastArticleId++;
      } else if (rq.getUrlPath().equals("/usr/article/detail")) {
        actionUsrArticleDetail(rq, articles);
      } else if (rq.getUrlPath().equals("/usr/article/list")) {
        actionUsrArticleList(rq, articles);
      } else if (rq.getUrlPath().equals("/usr/article/modify")) {
        actionUsrArticleModify(sc, rq, articles);
      } else if (rq.getUrlPath().equals("/usr/article/delete")) {
        actionUsrArticleDelete(rq, articles);
      } else if (rq.getUrlPath().equals("exit")) {
        break;
      } else {
        System.out.println("잘못 된 명령어입니다.");
      }
    }

    System.out.println("== 자바 텍스트 게시판 종료 ==");
    sc.close();
  }

  private static void actionUsrArticleDelete(Rq rq, List<Article> articles) {
    Map<String, String> params = rq.getParams();
    int id = 0;

    try {
      id = Integer.parseInt(params.get("id"));
    } catch (NumberFormatException e) {
      System.out.println("id를 정수 형태로 입력해주세요.");
      return;
    }

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    /*
    // 별도의 함수로 분리
    Article findArticle = null;

    for(Article article : articles) {
      if(article.id == id) {
        findArticle = article;
      }
    }
     */

    Article article = articleFindById(id, articles);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    articles.remove(article);
    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }

  private static void actionUsrArticleModify(Scanner sc, Rq rq, List<Article> articles) {
    Map<String, String> params = rq.getParams();
    int id = 0;

    try {
      id = Integer.parseInt(params.get("id"));
    } catch (NumberFormatException e) {
      System.out.println("id를 정수 형태로 입력해주세요.");
      return;
    }

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    Article article = articleFindById(id, articles);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.print("새 제목 : ");
    article.subject = sc.nextLine();

    System.out.print("새 내용 : ");
    article.content = sc.nextLine();

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  static void actionUsrArticleWrite(Scanner sc, List<Article> articles, int lastArticleId) {
    System.out.println("== 게시물 작성 ==");
    System.out.print("제목 : ");
    String subject = sc.nextLine();

    System.out.print("내용 : ");
    String content = sc.nextLine();

    int id = ++lastArticleId;

    Article article = new Article(id, subject, content); // 게시물 객체 생성

    articles.add(article);

    System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
  }

  static void actionUsrArticleDetail(Rq rq, List<Article> articles) {
    Map<String, String> params = rq.getParams();
    int id = 0;

    try {
      id = Integer.parseInt(params.get("id"));
    } catch (NumberFormatException e) {
      System.out.println("id를 정수 형태로 입력해주세요.");
      return;
    }

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    Article article = articleFindById(id, articles);

    if(article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.println("== 게시물 상세보기 ==");
    System.out.printf("번호 : %d\n", article.id);
    System.out.printf("제목 : %s\n", article.subject);
    System.out.printf("내용 : %s\n", article.content);
  }

  static void actionUsrArticleList(Rq rq, List<Article> articles) {
    Map<String, String> params = rq.getParams();

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    boolean orderByIdDesc = true;

    if (params.containsKey("orderBy") && params.get("orderBy").equals("idAsc")) {
      orderByIdDesc = false;
    }

    // 검색 시작
    List<Article> filteredArticles = articles;

    if (params.containsKey("searchKeyword")) {
      String searchKeyword = params.get("searchKeyword");

      filteredArticles = new ArrayList<>();

      for (Article article : articles) {
        if (article.subject.contains(searchKeyword) || article.content.contains(searchKeyword)) {
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

    if (orderByIdDesc) { // idAsc(오름차순)가 없으면 기본값인 idDesc(내림차순)
      soredArticles = Util.reverseList(soredArticles);
    }

    for (Article article : soredArticles) {
      System.out.printf("|   %d    |  %s  |\n", article.id, article.subject);
    }
  }

  private static Article articleFindById(int id, List<Article> articles) {
    for(Article article : articles) {
      if(article.id == id) {
        return article;
      }
    }

    return null;
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

  // 이 함수는 원본리스트를 훼손하지 않고, 새 리스트를 만듭니다.
  // 즉 정렬이 반대인 복사본리스트를 만들어서 반환합니다.
  public static <T> List<T> reverseList(List<T> list) {
    List<T> reverse = new ArrayList<>(list.size());

    for (int i = list.size() - 1; i >= 0; i--) {
      reverse.add(list.get(i));
    }
    return reverse;
  }
}

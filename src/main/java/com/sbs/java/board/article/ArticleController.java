package com.sbs.java.board.article;

import com.sbs.java.board.Rq;
import com.sbs.java.board.Util;
import com.sbs.java.board.container.Container;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ArticleController {
  private int lastArticleId;
  private List<Article> articles;

  public ArticleController() {
    lastArticleId = 0;
    articles = new ArrayList<>();

    makeTestData();

    if (!articles.isEmpty()) {
      lastArticleId = articles.get(articles.size() - 1).id;
    }
  }

  void makeTestData() {
    IntStream.rangeClosed(1, 100)
        .forEach(i -> articles.add(new Article(i, "제목" + i, "내용" + i)));
  }

  public void doWrite() {
    System.out.println("== 게시물 작성 ==");
    System.out.print("제목 : ");
    String subject = Container.scanner.nextLine();

    System.out.print("내용 : ");
    String content = Container.scanner.nextLine();

    int id = ++lastArticleId;

    Article article = new Article(id, subject, content); // 게시물 객체 생성

    articles.add(article);

    System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
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

  public void showList(Rq rq) {
    Map<String, String> params = rq.getParams();

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    String orderBy = rq.getParam("orderBy", "idDesc");

    boolean orderByIdDesc = orderBy.equals("idDesc");

    if(orderBy.equals("idAsc")) {
      orderByIdDesc = false;
    }

    // 검색 시작
    String searchKeyword = rq.getParam("searchKeyword", "");

    List<Article> filteredArticles = articles;

    if (!searchKeyword.isEmpty()) {

      filteredArticles = new ArrayList<>();

      for (Article article : articles) {
        boolean matched = article.subject.contains(searchKeyword) || article.content.contains(searchKeyword);

        if (matched) {
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

  public void doModify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
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
    article.subject = Container.scanner.nextLine();

    System.out.print("새 내용 : ");
    article.content = Container.scanner.nextLine();

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void doDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
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

    articles.remove(article);
    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }

  Article articleFindById(int id, List<Article> articles) {
    for(Article article : articles) {
      if(article.id == id) {
        return article;
      }
    }

    return null;
  }
}

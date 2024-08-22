package com.sbs.java.board.article;

import com.sbs.java.board.container.Container;

import java.util.List;
import java.util.stream.IntStream;

public class ArticleService {
  private ArticleRepository articleRepository;

  public ArticleService() {
    articleRepository = Container.articleRepository;
  }

  public void makeTestData() {
    IntStream.rangeClosed(1, 100)
        .forEach(i -> write("제목" + i, "내용" + i));
  }

  public int write(String subject, String content) {
    return articleRepository.write(subject, content);
  }

  public void modify(int id, String subject, String content) {
    articleRepository.modify(id, subject, content);
  }

  public void delete(int id) {
    articleRepository.delete(id);
  }

  public Article findById(int id) {
    return articleRepository.findById(id);
  }

  public List<Article> getArticles(String orderBy, String searchKeyword) {
    return articleRepository.getArticles(orderBy, searchKeyword);
  }
}

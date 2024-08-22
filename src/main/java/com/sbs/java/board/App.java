package com.sbs.java.board;

import com.sbs.java.board.article.ArticleController;
import com.sbs.java.board.container.Container;
import com.sbs.java.board.member.MemberController;

public class App {
  private ArticleController articleController;
  private MemberController memberController;

  public App() {
    articleController = Container.articleController;
    memberController = Container.memberController;
  }

  void run() {
    System.out.println("== 자바 텍스트 게시판 ==");

    while (true) {
      System.out.print("명령) ");
      String cmd = Container.scanner.nextLine();

      Rq rq = new Rq(cmd);

      if (rq.getUrlPath().equals("/usr/article/write")) {
        articleController.doWrite();
      } else if (rq.getUrlPath().equals("/usr/article/detail")) {
        articleController.showDetail(rq);
      } else if (rq.getUrlPath().equals("/usr/article/list")) {
        articleController.showList(rq);
      } else if (rq.getUrlPath().equals("/usr/article/modify")) {
        articleController.doModify(rq);
      } else if (rq.getUrlPath().equals("/usr/article/delete")) {
        articleController.doDelete(rq);
      } else if (rq.getUrlPath().equals("/usr/member/join")) {
        memberController.doJoin();
      } else if (rq.getUrlPath().equals("/usr/member/login")) {
        memberController.doLogin();
      } else if (rq.getUrlPath().equals("exit")) {
        break;
      } else {
        System.out.println("잘못 된 명령어입니다.");
      }
    }

    System.out.println("== 자바 텍스트 게시판 종료 ==");
    Container.scanner.close();
  }
}

package com.sbs.java.board.member;

import com.sbs.java.board.container.Container;

import java.util.ArrayList;
import java.util.List;

public class MemberController {
  private MemberService memberService;

  public MemberController() {
    memberService = Container.memberService;
  }

  public void doJoin() {
    String userId;
    String password;
    String passwordConfirm;
    String name;

    System.out.println("== 회원 가입 ==");

    // 로그인 아이디
    while (true) {
      System.out.print("로그인 아이디 : ");
      userId = Container.scanner.nextLine();

      if(userId.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      break;
    }

    // 로그인 비밀번호
    while (true) {
      System.out.print("로그인 비밀번호 : ");
      password = Container.scanner.nextLine();

      if(password.trim().isEmpty()) {
        System.out.println("로그인 비밀번호를 입력해주세요.");
        continue;
      }

      // 로그인 비밀번호 확인
      while (true) {
        System.out.print("로그인 비밀번호 확인 : ");
        passwordConfirm = Container.scanner.nextLine();

        if(passwordConfirm.trim().isEmpty()) {
          System.out.println("로그인 비밀번호 확인을 입력해주세요.");
          continue;
        }

        if(!passwordConfirm.equals(password)) {
          System.out.println("로그인 비밀번호가 일치하지 않습니다.");
          continue;
        }

        break;
      }

      break;
    }

    // 이름
    while (true) {
      System.out.print("이름 : ");
      name = Container.scanner.nextLine();

      if(name.trim().isEmpty()) {
        System.out.println("이름을 입력해주세요.");
        continue;
      }

      break;
    }

    int id = memberService.join(userId, password, name);

    System.out.printf("%d번 회원이 생성되었습니다.\n", id);
  }
}

package com.sbs.java.board.member;

import com.sbs.java.board.container.Container;

public class MemberController {
  private MemberService memberService;

  public MemberController() {
    memberService = Container.memberService;

    memberService.makeTestData();
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

      if (userId.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      Member member = memberService.findByUserId(userId);

      if (member != null) {
        System.out.printf("\"%s\"은(는) 이미 가입된 userId 입니다.\n", userId);
        continue;
      }

      break;
    }

    // 로그인 비밀번호
    while (true) {
      System.out.print("로그인 비밀번호 : ");
      password = Container.scanner.nextLine();

      if (password.trim().isEmpty()) {
        System.out.println("로그인 비밀번호를 입력해주세요.");
        continue;
      }

      // 로그인 비밀번호 확인
      while (true) {
        System.out.print("로그인 비밀번호 확인 : ");
        passwordConfirm = Container.scanner.nextLine();

        if (passwordConfirm.trim().isEmpty()) {
          System.out.println("로그인 비밀번호 확인을 입력해주세요.");
          continue;
        }

        if (!passwordConfirm.equals(password)) {
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

      if (name.trim().isEmpty()) {
        System.out.println("이름을 입력해주세요.");
        continue;
      }

      break;
    }

    int id = memberService.join(userId, password, name);

    System.out.printf("%d번 회원이 생성되었습니다.\n", id);
  }

  public void doLogin() {
    String userId;
    String password;
    Member member;

    System.out.println("== 로그인 ==");

    // 로그인 아이디
    while (true) {
      System.out.print("로그인 아이디 : ");
      userId = Container.scanner.nextLine();

      if (userId.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      member = memberService.findByUserId(userId);

      if (member == null) {
        System.out.printf("\"%s\"은(는) 존재하지 않습니다.\n", userId);
        continue;
      }

      break;
    }

    int tryMaxCount = 3;
    int tryCount = 0;

    // 로그인 비밀번호
    while (true) {
      if(tryCount == tryMaxCount) {
        System.out.println("비밀번호를 확인 후 다시 입력해주세요.");
        break;
      }

      System.out.print("로그인 비밀번호 : ");
      password = Container.scanner.nextLine();

      if (password.trim().isEmpty()) {
        System.out.println("로그인 비밀번호를 입력해주세요.");
        continue;
      }

      if(!member.getPassword().equals(password)) {
        tryCount++;
        System.out.printf("비밀번호를 틀렸습니다. / 틀린 횟수(%d / %d)\n", tryCount, tryMaxCount);
        continue;
      }

      System.out.printf("\"%s\"님 로그인되었습니다.\n", member.getName());

      break;
    }
  }
}

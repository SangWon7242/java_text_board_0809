package com.sbs.java.board.member;

import com.sbs.java.board.container.Container;

import java.util.stream.IntStream;

public class MemberService {
  private MemberRepository memberRepository;

  public MemberService() {
    memberRepository = Container.memberRepository;
  }

  public void makeTestData() {
    IntStream.rangeClosed(1, 5)
        .forEach(i -> join("user" + i, "user" + i, "회원" + i));
  }

  public int join(String userId, String password, String name) {
    return memberRepository.join(userId, password, name);
  }
}

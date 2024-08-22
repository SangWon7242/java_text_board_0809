package com.sbs.java.board.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
  private int id;
  private String userId;
  private String password;
  private String name;
}

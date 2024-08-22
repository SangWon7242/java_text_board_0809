package com.sbs.java.board.article;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Article {
  private int id;
  private String subject;
  private String content;
}

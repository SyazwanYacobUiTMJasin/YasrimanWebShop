package com.masbro.yasriman.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGptRequestMessage {


  private String role;
  private String content;


  public ChatGptRequestMessage(ApiRequest request) {
this.role = "user";
this.content = request.getMessage();
  }
}
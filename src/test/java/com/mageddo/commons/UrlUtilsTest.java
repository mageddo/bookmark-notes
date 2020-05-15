package com.mageddo.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlUtilsTest {

  @Test
  void mustKeepPathAsItIs() {

    // arrange

    // act
    final var encodeSeoUrl = UrlUtils.encodeSeoUrl("my-website");

    //assert
    assertEquals("my-website", encodeSeoUrl);
  }

  @Test
  void mustRemoveAllSpecialChars() {

    // arrange

    // act
    final var encodeSeoUrl = UrlUtils.encodeSeoUrl("999-green/red-fruits-are-great!!!");

    //assert
    assertEquals("999-green-red-fruits-are-great", encodeSeoUrl);
  }

  @Test
  void mustRemoveSpecialCharsAtTheStart() {

    // arrange

    // act
    final var encodeSeoUrl = UrlUtils.encodeSeoUrl("&!/999-green/red-fruits-are-great!!!");

    //assert
    assertEquals("999-green-red-fruits-are-great", encodeSeoUrl);
  }

  @Test
  void mustRemoveSpecialCharsAtTheMid() {

    // arrange

    // act
    final var encodeSeoUrl = UrlUtils.encodeSeoUrl("&!/999-green!*/red-fruits-are-great!!!");

    //assert
    assertEquals("999-green-red-fruits-are-great", encodeSeoUrl);
  }
}

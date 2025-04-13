package com.example.utils

object TextCleaner {
  def cleanText(text: String): String = {
    text.replaceAll("\\*\\s\\*\\*|\\*\\*|\\*", "").trim
  }
}


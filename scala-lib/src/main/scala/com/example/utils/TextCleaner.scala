package com.example.utils

object TextCleaner {
  def cleanText(text: String): String = {
    text.replaceAll("[^a-zA-Z0-9\\s]", "").trim
  }
}


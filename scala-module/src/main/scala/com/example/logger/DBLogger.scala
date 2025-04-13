package com.example.logger

case class SummaryEntry(url: String, summary: String)

object DBLogger {
  def logToPostgres(entry: SummaryEntry): Boolean = {
    println(s"Logging: ${entry.url} => ${entry.summary}")
    true
  }
}

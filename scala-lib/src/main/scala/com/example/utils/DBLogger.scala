package com.example.utils

import java.sql.{Connection, DriverManager, PreparedStatement, Timestamp}
import java.time.LocalDateTime

object DBLogger {
  val dbUrl = "jdbc:postgresql://localhost:5432/web_summarizer"
  val dbUser = "postgres"
  val dbPassword = "postgres"

  def logSummary(url: String, summary: String): Unit = {
    val conn: Connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)
    val stmt: PreparedStatement = conn.prepareStatement(
      "INSERT INTO summary (url, summary, created_at) VALUES (?, ?, ?)"
    )
    stmt.setString(1, url)
    stmt.setString(2, summary)
    stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()))
    stmt.executeUpdate()
    stmt.close()
    conn.close()
  }
}

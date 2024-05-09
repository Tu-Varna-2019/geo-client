package com.tuvarna.geo.helpers

import android.util.Patterns
import com.tuvarna.geo.rest_api.models.LoggerDTO

class Utils {
  companion object {
    fun isValidEmail(email: String): Boolean {
      return !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // chars >= 8 chars, lowercase + uppercase +digits
    fun isValidPassword(password: String): Boolean {
      val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$".toRegex()
      return !password.matches(passwordRegex)
    }

    fun filterLogsByRegex(searchText: String, userLogs: List<LoggerDTO>): List<LoggerDTO> {
      // Looking for any of the words <word 1> <word 2> <word 3>
      val regexAnyOfWords = """\b(\w+)\s+(\w+)\s+(\w+)\b""".toRegex()

      // Looking for the whole phrase "<word 1> <word2> <word3>"
      val regexWholeWord = """"([^"]+)"\s+("[^"]+")\s+("[^"]+")""".toRegex()

      // Seek <value> using
      // Full-text search (see the rules above) but only for the <key field>
      // "<key> : <value>"
      val regexWholeWordByKeyVal = """(\w+)\s*:\s*(\w+)""".toRegex()

      // Looking for an accurate match of <Value> for the <Key> field. For example
      // “Datatype = the soil” will look for Datatype equal to 'soil'
      // "<key> = <value>"
      val regexAccurrateWordByKeyVal = """(\w+)\s*=\s*(\w+)""".toRegex()

      return when {
        regexAnyOfWords.matches(searchText) -> {
          val (word1, word2, word3) = regexAnyOfWords.find(searchText)!!.destructured
          userLogs.filter { log ->
            listOf(word1, word2, word3).all { word ->
              log.username!!.contains(word) || log.ip!!.contains(word) || log.event!!.contains(word)
            }
          }
        }
        else -> userLogs
      }
    }
  }
}

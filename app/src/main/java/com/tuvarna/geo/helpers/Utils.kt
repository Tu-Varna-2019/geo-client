package com.tuvarna.geo.helpers

import android.util.Patterns
import com.tuvarna.geo.rest_api.models.LoggerDTO
import timber.log.Timber

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
      val regexAnyOfWords = """\b\w+(?:\s+\w+)*\b""".toRegex()

      // Looking for the whole phrase "<word 1> <word2> <word3>"
      val regexWholeWord = """"([^"]+)"""".toRegex()

      // Seek <value> using
      // Full-text search (see the rules above) but only for the <key field>
      // "<key> : <value>"
      val regexWholeWordByKeyVal = """(\w+)\s*:\s*("[^"]+"|\w+)""".toRegex()

      // Looking for an accurate match of <Value> for the <Key> field. For example
      // “Datatype = the soil” will look for Datatype equal to 'soil'
      // "<key> = <value>"
      val regexAccurateWordByKeyVal = """(\w+)\s*=\s*("[^"]+"|\S+)""".toRegex()

      return when {
        regexAnyOfWords.matches(searchText) -> {
          Timber.e(
            "Search pattern invoked for looking for any of the words <word 1> <word 2> <word 3>"
          )
          val words = regexAnyOfWords.findAll(searchText).map { it.value }.toList()
          userLogs.filter { log ->
            words.any { word ->
              log.username?.contains(word) ?: false ||
                log.ip?.contains(word) ?: false ||
                log.event?.contains(word) ?: false
            }
          }
        }
        regexWholeWord.matches(searchText) -> {
          Timber.e(
            "Search pattern invoked for looking for the whole phrase \"<word 1> <word2> <word3>\"\n"
          )

          val word = regexWholeWord.find(searchText)!!.groupValues[1]
          userLogs.filter { log ->
            log.username!! == word || log.ip!! == word || log.event!! == word
          }
        }
        regexWholeWordByKeyVal.matches(searchText) -> {
          Timber.e("Search pattern invoked for Full-text search '<key> : <value>'")

          val (key, value) = regexWholeWordByKeyVal.find(searchText)!!.destructured
          userLogs.filter { log ->
            when (key) {
              "username" -> log.username == value.trim('"')
              "ip" -> log.ip == value.trim('"')
              "event" -> log.event == value.trim('"')
              else -> false
            }
          }
        }
        regexAccurateWordByKeyVal.matches(searchText) -> {
          Timber.e(
            "Search pattern invoked for accurate match of <Value> for the <Key> field '<key> =  <value>'"
          )

          val (key, value) = regexAccurateWordByKeyVal.find(searchText)!!.destructured
          userLogs.filter { log ->
            when (key) {
              "username" -> log.username == value.trim('"')
              "ip" -> log.ip == value.trim('"')
              "event" -> log.event == value.trim('"')
              else -> false
            }
          }
        }
        else -> userLogs
      }
    }
  }
}

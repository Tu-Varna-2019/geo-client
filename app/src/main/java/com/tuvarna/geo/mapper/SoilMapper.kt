package com.tuvarna.geo.mapper

import android.content.Context
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.tuvarna.geo.R

class SoilMapper(
  context: Context,
  private val domsoi: String?,
  private val faosoi: String,
  private val phases: String?,
  private val misc1: String?,
  private val misc2: String?,
) {

  var domsoiFullDescription: String = ""
  var phaseFullDescription: String = ""
  var misc1FullDescription: String = ""
  var misc2FullDescription: String = ""
  var mappingUnit: String = ""
  var textureClass: String = ""
  var slopeClass: String = ""

  init {
    readSoilConfig(context)
  }

  fun readSoilConfig(context: Context) {

    val domsoiJsonPath =
      context.resources.openRawResource(R.raw.domsois).bufferedReader().use { it.readText() }
    val phasesJsonPath =
      context.resources.openRawResource(R.raw.phases).bufferedReader().use { it.readText() }
    val miscJsonPath =
      context.resources.openRawResource(R.raw.misclus).bufferedReader().use { it.readText() }

    val domsoiData: Map<String, Any> =
      ObjectMapper().readValue(domsoiJsonPath, object : TypeReference<Map<String, Any>>() {})
    val phasesData: Map<String, Any> =
      ObjectMapper().readValue(phasesJsonPath, object : TypeReference<Map<String, Any>>() {})
    val miscData: Map<String, Any> =
      ObjectMapper().readValue(miscJsonPath, object : TypeReference<Map<String, Any>>() {})

    mappingUnit = resolveMappingUnit(domsoi)
    slopeClass = resolveSlopeClass(faosoi)
    textureClass = resolveTextureClass(faosoi)

    domsoiFullDescription = (domsoiData[domsoi] ?: "Not found").toString()
    phaseFullDescription = (phasesData[phases] ?: "").toString()

    misc1FullDescription = (miscData[misc1] ?: "").toString()
    misc2FullDescription = (miscData[misc2] ?: "").toString()
  }

  private fun resolveMappingUnit(domsoi: String?): String {
    when (domsoi) {
      "Bk" -> {
        return "Dominant 40%"
      }
      "K",
      "E" -> {
        return "Associated 20%"
      }
      "Jc",
      "Zo" -> {
        return "Inclusion 10%"
      }
    }
    return ""
  }

  private fun resolveTextureClass(faosoi: String): String {
    /*
    2/3
     */
    val result: StringBuilder = StringBuilder()
    if (faosoi.contains("-")) {
      var textureClassCharSize: Int = faosoi.indexOf("-") + 1
      while (
        textureClassCharSize < faosoi.length &&
          (faosoi[textureClassCharSize].isDigit() || faosoi[textureClassCharSize] == '/')
      ) {
        result.append(faosoi[textureClassCharSize++])
      }
    }
    return result.toString()
  }

  private fun resolveSlopeClass(faosoi: String): String {
    /*
    ab,a,b,c
     */
    if (faosoi.last().toString() in setOf("a", "b", "c")) {
      var slopeClassCharSize: Int = if (faosoi[faosoi.length - 2].isDigit()) 1 else 2
      var result: String = ""
      while (slopeClassCharSize > 0) {
        result += faosoi[faosoi.length - (slopeClassCharSize--)].toString()
      }
      return result
    }
    return ""
  }
}

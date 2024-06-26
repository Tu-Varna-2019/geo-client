/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.tuvarna.geo.controller.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 *
 *
 * @param `null`
 * @param width
 * @param height
 * @param diameter
 * @param minX
 * @param maxX
 * @param minY
 * @param maxY
 * @param area
 */


data class Envelope (

    @Json(name = "null")
    val `null`: kotlin.Boolean? = null,

    @Json(name = "width")
    val width: kotlin.Double? = null,

    @Json(name = "height")
    val height: kotlin.Double? = null,

    @Json(name = "diameter")
    val diameter: kotlin.Double? = null,

    @Json(name = "minX")
    val minX: kotlin.Double? = null,

    @Json(name = "maxX")
    val maxX: kotlin.Double? = null,

    @Json(name = "minY")
    val minY: kotlin.Double? = null,

    @Json(name = "maxY")
    val maxY: kotlin.Double? = null,

    @Json(name = "area")
    val area: kotlin.Double? = null

)


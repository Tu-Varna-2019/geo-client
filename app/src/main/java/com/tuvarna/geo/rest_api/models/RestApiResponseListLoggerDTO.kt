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

package com.tuvarna.geo.rest_api.models

import com.tuvarna.geo.rest_api.models.LoggerDTO

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param `data` 
 * @param message 
 * @param status 
 */


data class RestApiResponseListLoggerDTO (

    @Json(name = "data")
    val `data`: kotlin.collections.List<LoggerDTO>? = null,

    @Json(name = "message")
    val message: kotlin.String? = null,

    @Json(name = "status")
    val status: kotlin.Int? = null

)

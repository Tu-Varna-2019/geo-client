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


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param email 
 * @param password 
 */


data class LoginUserDTO (

    @Json(name = "email")
    val email: kotlin.String? = null,

    @Json(name = "password")
    val password: kotlin.String? = null

)


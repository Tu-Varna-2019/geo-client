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

import com.tuvarna.geo.rest_api.models.PrecisionModel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param precisionModel 
 * @param coordinateSequenceFactory 
 * @param srid 
 */


data class GeometryFactory (

    @Json(name = "precisionModel")
    val precisionModel: PrecisionModel? = null,

    @Json(name = "coordinateSequenceFactory")
    val coordinateSequenceFactory: kotlin.Any? = null,

    @Json(name = "srid")
    val srid: kotlin.Int? = null

)


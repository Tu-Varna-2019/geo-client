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

import com.tuvarna.geo.rest_api.models.Coordinate
import com.tuvarna.geo.rest_api.models.Envelope
import com.tuvarna.geo.rest_api.models.GeometryFactory
import com.tuvarna.geo.rest_api.models.Point
import com.tuvarna.geo.rest_api.models.PrecisionModel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param envelope 
 * @param factory 
 * @param userData 
 * @param length 
 * @param empty 
 * @param valid 
 * @param simple 
 * @param srid 
 * @param precisionModel 
 * @param rectangle 
 * @param centroid 
 * @param interiorPoint 
 * @param boundaryDimension 
 * @param coordinate 
 * @param coordinates 
 * @param geometryType 
 * @param boundary 
 * @param numGeometries 
 * @param numPoints 
 * @param area 
 * @param envelopeInternal 
 * @param dimension 
 */


data class Geometry (

    @Json(name = "envelope")
    val envelope: Geometry? = null,

    @Json(name = "factory")
    val factory: GeometryFactory? = null,

    @Json(name = "userData")
    val userData: kotlin.Any? = null,

    @Json(name = "length")
    val length: kotlin.Double? = null,

    @Json(name = "empty")
    val empty: kotlin.Boolean? = null,

    @Json(name = "valid")
    val valid: kotlin.Boolean? = null,

    @Json(name = "simple")
    val simple: kotlin.Boolean? = null,

    @Json(name = "srid")
    val srid: kotlin.Int? = null,

    @Json(name = "precisionModel")
    val precisionModel: PrecisionModel? = null,

    @Json(name = "rectangle")
    val rectangle: kotlin.Boolean? = null,

    @Json(name = "centroid")
    val centroid: Point? = null,

    @Json(name = "interiorPoint")
    val interiorPoint: Point? = null,

    @Json(name = "boundaryDimension")
    val boundaryDimension: kotlin.Int? = null,

    @Json(name = "coordinate")
    val coordinate: Coordinate? = null,

    @Json(name = "coordinates")
    val coordinates: kotlin.collections.List<Coordinate>? = null,

    @Json(name = "geometryType")
    val geometryType: kotlin.String? = null,

    @Json(name = "boundary")
    val boundary: Geometry? = null,

    @Json(name = "numGeometries")
    val numGeometries: kotlin.Int? = null,

    @Json(name = "numPoints")
    val numPoints: kotlin.Int? = null,

    @Json(name = "area")
    val area: kotlin.Double? = null,

    @Json(name = "envelopeInternal")
    val envelopeInternal: Envelope? = null,

    @Json(name = "dimension")
    val dimension: kotlin.Int? = null

)


package com.tuvarna.geo.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import com.tuvarna.geo.rest_api.models.DangerDTO

class PointEntity {

  var latitude by mutableDoubleStateOf(0.00)
  var longitude by mutableDoubleStateOf(0.00)

  constructor(latitude: Double, longitude: Double) {
    this.latitude = latitude
    this.longitude = longitude
  }

  fun toDangerDto(dangerDTO: DangerDTO): DangerDTO {
    return DangerDTO()
  }

  @Override
  override fun toString(): String {
    return "Point(latitude='$latitude', longitude='$longitude')"
  }
}

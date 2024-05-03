package com.tuvarna.geo.entity

import com.tuvarna.geo.rest_api.models.Earthquake
import com.tuvarna.geo.rest_api.models.Soil

enum class RiskChoices {
  None,
  Soil,
  Earthquake,
}

sealed class RiskHierarchy {
  object NoDataYet : RiskHierarchy()

  data class SoilSubHierarchy(val soil: Soil) : RiskHierarchy()

  data class EarthquakeSubHierarchy(val earthquake: Earthquake) : RiskHierarchy()
}

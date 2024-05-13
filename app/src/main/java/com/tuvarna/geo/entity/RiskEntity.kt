package com.tuvarna.geo.entity

import com.tuvarna.geo.controller.models.Earthquake
import com.tuvarna.geo.controller.models.Soil

enum class RiskChoices {
  None,
  Soil,
  Earthquake,
}

class RiskHierarchy(
  var riskState: RiskChoices = RiskChoices.None,
  private var _soil: Soil = Soil(),
  private var _earthquake: Earthquake = Earthquake(),
) {
  var soil: Soil
    get() {
      riskState = RiskChoices.Soil
      return _soil
    }
    set(value) {
      _soil = value
      if (value != Soil()) {
        riskState = RiskChoices.Soil
      }
    }

  var earthquake: Earthquake
    get() {
      riskState = RiskChoices.Earthquake
      return _earthquake
    }
    set(value) {
      _earthquake = value
      if (value != Earthquake()) {
        riskState = RiskChoices.Earthquake
      }
    }
}

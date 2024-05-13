package com.tuvarna.geo.navigation

class UIFeedback(state: States, message: String = "") {
  private var _state: States = state
  private var _message: String = message
  var state: States
    get() = _state
    set(value) {
      if (value in States.entries.toTypedArray()) _state = value
    }

  var message: String
    get() = _message
    set(value) {
      _message = value
    }

  enum class States {
    Nothing,
    Waiting,
    Failed,
    Success,
  }
}

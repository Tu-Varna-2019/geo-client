package com.tuvarna.geo.viewmodel

import androidx.lifecycle.ViewModel
import com.tuvarna.geo.controller.UIFeedback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class UIStateViewModel : ViewModel() {
  protected val mutableStateFlow =
    MutableStateFlow<UIFeedback>(UIFeedback(state = UIFeedback.States.Nothing))
  val stateFlow: StateFlow<UIFeedback> = mutableStateFlow
  protected var returnStatus: UIFeedback.States = UIFeedback.States.Nothing
}

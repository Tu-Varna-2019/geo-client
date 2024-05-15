package com.tuvarna.geo.viewmodel.states

import androidx.lifecycle.ViewModel
import com.tuvarna.geo.navigation.UIFeedback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class UIState() : ViewModel() {
  protected val mutableStateFlow =
    MutableStateFlow<UIFeedback>(UIFeedback(state = UIFeedback.States.Nothing))
  val stateFlow: StateFlow<UIFeedback> = mutableStateFlow
  protected var returnStatus: UIFeedback.States = UIFeedback.States.Nothing
}

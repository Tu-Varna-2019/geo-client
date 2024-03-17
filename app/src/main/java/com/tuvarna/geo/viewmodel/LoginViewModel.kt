import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuvarna.geo.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

  private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Empty)
  val uiState: StateFlow<LoginUiState> = _uiState

  fun login(username: String, password: String) {
    viewModelScope.launch {
      _uiState.value = LoginUiState.Loading
      try {
        val result = userRepository.login(username, password)
        _uiState.value = LoginUiState.Success
      } catch (e: Exception) {
        _uiState.value = LoginUiState.Error(e.message ?: "An unknown error occurred")
      }
    }
  }
}

sealed class LoginUiState {
  object Empty : LoginUiState()

  object Loading : LoginUiState()

  object Success : LoginUiState()

  data class Error(val message: String) : LoginUiState()
}

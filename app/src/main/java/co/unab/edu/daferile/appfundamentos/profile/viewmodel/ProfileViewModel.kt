package co.unab.edu.daferile.appfundamentos.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.unab.edu.daferile.appfundamentos.profile.domain.GetUserUseCase
import co.unab.edu.daferile.appfundamentos.profile.screen.ui.UserUIState
import co.unab.edu.daferile.appfundamentos.profile.screen.ui.UserUIState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(getUserUseCase: GetUserUseCase): ViewModel() {
    val uiState: StateFlow<UserUIState> =
        getUserUseCase("piccolo@gmail.com").map(::Success).catch { error ->
            UserUIState.Error(error)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserUIState.Loading)
}
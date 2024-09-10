package co.unab.edu.daferile.appfundamentos.users.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.unab.edu.daferile.appfundamentos.users.domain.GetUsersListUseCase
import co.unab.edu.daferile.appfundamentos.users.screen.ui.UserListUIState
import co.unab.edu.daferile.appfundamentos.users.screen.ui.UserListUIState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(getUsersListUseCase: GetUsersListUseCase): ViewModel(){
    val uiState: StateFlow<UserListUIState> =
        getUsersListUseCase().map(::Success).catch { error ->
            UserListUIState.Error(error)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserListUIState.Loading)
}
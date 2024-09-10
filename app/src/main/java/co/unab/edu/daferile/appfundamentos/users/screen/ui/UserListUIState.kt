package co.unab.edu.daferile.appfundamentos.users.screen.ui

import co.unab.edu.daferile.appfundamentos.core.ui.model.User

sealed interface UserListUIState {
    data object Loading : UserListUIState
    data class Success(val userList: List<User>): UserListUIState
    data class Error(val throwable: Throwable): UserListUIState
}
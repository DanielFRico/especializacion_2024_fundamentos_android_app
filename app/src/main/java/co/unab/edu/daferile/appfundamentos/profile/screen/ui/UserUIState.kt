package co.unab.edu.daferile.appfundamentos.profile.screen.ui

import co.unab.edu.daferile.appfundamentos.core.ui.model.User

interface UserUIState {
    data object Loading : UserUIState
    data class Success(val user: User): UserUIState
    data class Error(val throwable: Throwable): UserUIState
}
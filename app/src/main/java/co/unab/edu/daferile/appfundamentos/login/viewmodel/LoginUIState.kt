package co.unab.edu.daferile.appfundamentos.login.viewmodel

sealed interface LoginUIState {
    data object Loading : LoginUIState
    data object Default : LoginUIState
    data object Success : LoginUIState
    data object Error : LoginUIState
}
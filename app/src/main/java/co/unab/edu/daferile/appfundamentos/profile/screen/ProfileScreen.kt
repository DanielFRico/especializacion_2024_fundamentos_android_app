package co.unab.edu.daferile.appfundamentos.profile.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import co.unab.edu.daferile.appfundamentos.core.ui.model.User
import co.unab.edu.daferile.appfundamentos.profile.screen.ui.UserUIState
import co.unab.edu.daferile.appfundamentos.profile.viewmodel.ProfileViewModel
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<UserUIState>(
        initialValue = UserUIState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { state -> value = state }
        }
    }
    when (uiState) {
        is UserUIState.Error -> {}
        is UserUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        is UserUIState.Success -> {
            val user = (uiState as UserUIState.Success).user
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                UserItem(user = user)
            }
        }
    }

}


@Composable
fun UserItem(user: User) {
    ConstraintLayout(
        modifier = Modifier
            .padding(10.dp)
    ) {
        val (image, name, email, document) = createRefs()
        AsyncImage(
            model = user.image,
            contentDescription = "image",
            modifier = Modifier
                .size(120.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                })
        Text(text = user.name, modifier = Modifier.constrainAs(name) {
            start.linkTo(parent.start)
            top.linkTo(image.bottom)
        }, fontFamily = FontFamily.Monospace, fontSize = 25.sp)
        Text(text = user.email, modifier = Modifier.constrainAs(email) {
            start.linkTo(parent.start)
            top.linkTo(name.bottom)
        })
        Text(text = user.document.toString(), modifier = Modifier.constrainAs(document) {
            start.linkTo(parent.start)
            top.linkTo(email.bottom)
        })
    }
}

package co.unab.edu.daferile.appfundamentos.users.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import co.unab.edu.daferile.appfundamentos.core.ui.model.User
import co.unab.edu.daferile.appfundamentos.users.screen.ui.UserListUIState
import co.unab.edu.daferile.appfundamentos.users.viewmodel.UserViewModel
import coil.compose.AsyncImage

@Composable
fun UsersScreen(modifier: Modifier, viewModel: UserViewModel) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<UserListUIState>(
        initialValue = UserListUIState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { state -> value = state }
        }
    }
    when (uiState) {
        is UserListUIState.Error -> {}
        is UserListUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        is UserListUIState.Success -> {
            val usersList = (uiState as UserListUIState.Success).userList
            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {
                items(usersList) {
                    UserItem(user = it) { user ->
                        Toast.makeText(context, "$user", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}

@Composable
fun UserItem(user: User, onSelectUser: (User) -> Unit) {
    Card(
        onClick = { onSelectUser(user) }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(10.dp)
        ) {
            val (image, name, email) = createRefs()
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
            }, color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 25.sp)
            Text(text = user.email, modifier = Modifier.constrainAs(email) {
                start.linkTo(parent.start)
                top.linkTo(name.bottom)
            }, color = Color.White)
        }
    }
}

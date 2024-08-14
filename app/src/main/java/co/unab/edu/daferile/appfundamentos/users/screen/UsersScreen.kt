package co.unab.edu.daferile.appfundamentos.users.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import co.unab.edu.daferile.appfundamentos.core.ui.model.User
import coil.compose.AsyncImage

@Composable
fun UsersScreen(modifier: Modifier) {
    val context = LocalContext.current
    LazyRow(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(loadUsers()) {
            UserItem(user = it) { user ->
                Toast.makeText(context, "$user", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun UserItem(user: User, onSelectUser: (User) -> Unit) {
    Card(
        onClick = { onSelectUser(user) }, modifier = Modifier
            .height(200.dp)
    ) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)) {
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
            }, color = Color.White, fontFamily = FontFamily.Monospace, fontSize = 30.sp)
            Text(text = user.email, modifier = Modifier.constrainAs(email) {
                start.linkTo(parent.start)
                top.linkTo(name.bottom)
            }, color = Color.White)
        }
    }
}

private fun loadUsers(): List<User> {
    return listOf()
}
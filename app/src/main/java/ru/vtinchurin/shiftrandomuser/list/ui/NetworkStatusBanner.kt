package ru.vtinchurin.shiftrandomuser.list.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.vtinchurin.shiftrandomuser.list.presentation.UserListState

@Composable
fun NetworkStatusBanner(
    state: UserListState,
    modifier: Modifier = Modifier,
) {

    AnimatedVisibility(
        visible = state.error != null, enter = slideInVertically(
            initialOffsetY = { -it }, animationSpec = tween(durationMillis = 300)
        ) + fadeIn(animationSpec = tween(durationMillis = 300)), exit = slideOutVertically(
            targetOffsetY = { -it }, animationSpec = tween(durationMillis = 300)
        ) + fadeOut(animationSpec = tween(durationMillis = 300))
    ) {

        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp), colors = CardColors(
                containerColor = Color.Red,
                contentColor = Color.White,
                disabledContentColor = Color.DarkGray,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(
                state.error ?: "",
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
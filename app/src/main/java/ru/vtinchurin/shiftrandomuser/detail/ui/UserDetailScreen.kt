package ru.vtinchurin.shiftrandomuser.detail.ui

import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.vtinchurin.shiftrandomuser.detail.presentation.UserDetailScreenState
import ru.vtinchurin.shiftrandomuser.detail.presentation.UserDetailViewModel
import ru.vtinchurin.shiftrandomuser.detail.presentation.UserUi
import ru.vtinchurin.shiftrandomuser.utils.ui.shimmerLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: Long,
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = koinViewModel(
        parameters = {
            parametersOf(userId)
        }),
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("User details")
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.clickable {
                            onBackPressed()
                        }
                    )
                },
            )
        }) { padding ->

        val state = viewModel.state.collectAsStateWithLifecycle()
        Content(
            state = state,
            modifier = modifier.padding(padding)
        )
    }
}

@Composable
private fun Content(
    state: State<UserDetailScreenState>,
    modifier: Modifier = Modifier,
) {

    Box(modifier = modifier) {
        UserDetails(
            user = state.value.user
        )
    }
}

@Composable
private fun UserDetails(user: UserUi) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val imageMinSize = 36.dp
    val imageMaxSize = 300.dp
    var imageSize by remember { mutableStateOf(imageMaxSize) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                fun deltaDp() = with(density) { available.y.toDp() }

                if (available.y < 0 && imageSize > imageMinSize) {
                    imageSize = max(imageMinSize, imageSize + deltaDp())
                }

                if (available.y > 0 && imageSize < imageMaxSize) {
                    imageSize = min(imageMaxSize, imageSize + deltaDp())
                }
                return Offset.Zero
            }
        }
    }

    Box(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        contentAlignment = Alignment.TopCenter
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(imageSize)
                    .border(1.dp, Color.LightGray, CircleShape)
                    .clip(CircleShape), contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = user.imageUrl,
                    contentDescription = "User profile picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .shimmerLoading()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {

                Text(
                    text = user.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email
                Text("Email:", style = MaterialTheme.typography.labelLarge)
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = "mailto:${user.email}".toUri()
                        }
                        context.startActivity(intent)
                    },
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Phone
                Text("Phone:", style = MaterialTheme.typography.labelLarge)
                Text(
                    text = user.phone,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = "tel:${user.phone}".toUri()
                        }
                        context.startActivity(intent)
                    },
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Spacer(modifier = Modifier.height(8.dp))

                // Address
                Text("Address:", style = MaterialTheme.typography.labelLarge)
                Text(
                    text = user.city,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = "geo:0,0?q=${user.city}".toUri()
                        }
                        context.startActivity(intent)
                    },
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
package ru.vtinchurin.shiftrandomuser.list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.vtinchurin.shiftrandomuser.list.presentation.UserListState
import ru.vtinchurin.shiftrandomuser.list.presentation.UserListViewModel
import ru.vtinchurin.shiftrandomuser.utils.ui.shimmerLoading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    viewModel: UserListViewModel = koinViewModel(),
    onUserClick: (Long) -> Unit,
) {
    val screenState = viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Users")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            InternalUserListScreen(
                state = screenState.value,
                modifier = Modifier,
                onRefresh = viewModel::refresh,
                onUserClick = onUserClick,
            )
            NetworkStatusBanner(
                screenState.value,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InternalUserListScreen(
    state: UserListState,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    onUserClick: (Long) -> Unit,
) {
    val listState: LazyListState = rememberLazyListState()
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(state) {
        if (!state.isLoading) {
            pullToRefreshState.animateToHidden()
        }
    }
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = onRefresh,
        modifier = modifier,
        state = pullToRefreshState,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = state.isLoading,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = pullToRefreshState
            )
        }) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = state.users, key = { user -> user.email + user.hashCode() }) { user ->
                UserCard(
                    modifier = Modifier
                        .animateItem()
                        .clickable {
                            onUserClick(user.id)
                        },
                    name = user.name,
                    imageUrl = user.imageUrl,
                    phone = user.phone,
                    address = user.city,
                    email = user.email,
                )
            }
        }
    }
}

@Composable
fun UserCard(
    name: String,
    email: String,
    phone: String,
    address: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(max = 100.dp)
                    .widthIn(max = 100.dp),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    model = imageUrl,
                    modifier = Modifier
                        .clip(CircleShape)
                        .shimmerLoading()
                        .size(100.dp),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }

            Column(
                modifier = Modifier
                    .weight(2f)
                    .heightIn(max = 100.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = name, style = MaterialTheme.typography.titleLarge)
                AddressHolderUi(address)
                EmailHolderUi(email)
                PhoneHolderUi(phone)
            }
        }
    }
}

@Composable
fun PhoneHolderUi(phone: String) {
    val number = formatPhoneNumber(phone)
    Text(
        text = number,
        textDecoration = TextDecoration.Underline,
        style = MaterialTheme.typography.bodySmall,
    )
}

@Composable
fun EmailHolderUi(email: String) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Outlined.Email,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = email,
            textDecoration = TextDecoration.Underline,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
fun AddressHolderUi(address: String) {
    Text(
        text = address,
        textDecoration = TextDecoration.None,
        style = MaterialTheme.typography.bodySmall,
    )
}

fun formatPhoneNumber(phone: String): String {
    val digits = phone.replace(Regex("[^0-9]"), "")
    return when {
        digits.length == 11 && digits.startsWith("8") -> digits.replace(
            Regex("(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})"), "+7 ($2) $3-$4-$5"
        )

        digits.length == 11 && digits.startsWith("7") -> digits.replace(
            Regex("(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})"), "+$1 ($2) $3-$4-$5"
        )

        digits.length == 10 -> digits.replace(
            Regex("(\\d{3})(\\d{3})(\\d{2})(\\d{2})"), "($1) $2-$3-$4"
        )

        digits.length == 6 -> digits.replace(Regex("(\\d{2})(\\d{2})(\\d{2})"), "$1-$2-$3")

        else -> phone
    }
}
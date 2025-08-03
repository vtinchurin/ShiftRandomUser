package ru.vtinchurin.shiftrandomuser.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ru.vtinchurin.shiftrandomuser.detail.ui.UserDetailScreen
import ru.vtinchurin.shiftrandomuser.list.ui.UserListScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {

    NavHost(navController, startDestination = Screen.MainGraph.UserList) {

        composable<Screen.MainGraph.UserList> {
            UserListScreen(
                modifier = Modifier.fillMaxSize()
            ) { userId ->
                navController.navigate(Screen.MainGraph.UserDetail(userId))
            }
        }

        composable<Screen.MainGraph.UserDetail> {
            val screen: Screen.MainGraph.UserDetail = it.toRoute()
            val userId = screen.id
            UserDetailScreen(
                userId = userId,
                modifier = Modifier.fillMaxSize(),
            ) {
                navController.popBackStack()
            }
        }
    }
}
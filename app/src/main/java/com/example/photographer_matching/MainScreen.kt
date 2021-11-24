package com.example.photographer_matching


import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.*


sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Frankendroid : BottomNavigationScreens("Home", R.string.home, Icons.Filled.Terrain)
    object Pumpkin : BottomNavigationScreens("Search", R.string.search, Icons.Filled.FoodBank)
    object Ghost : BottomNavigationScreens("MyPage", R.string.my_page, Icons.Filled.Menu)
}

//sealed class ScaryAnimation(val animId: Int){
//    object Frankendroid: ScaryAnimation(R.raw.frankensteindroid)
//    object Pumpkin: ScaryAnimation(R.raw.jackolantern)
//    object Ghost: ScaryAnimation(R.raw.ghost)
//    object ScaryBag: ScaryAnimation(R.raw.bag)
//}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Frankendroid,
        BottomNavigationScreens.Pumpkin,
        BottomNavigationScreens.Ghost
    )
    Scaffold(
        bottomBar = {
            SppokyAppBottomNavigation(navController, bottomNavigationItems)
        },
    ) {
        MainScreenNavigationConfigurations(navController)
    }
}

@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController
) {
    NavHost(navController, startDestination = BottomNavigationScreens.Frankendroid.route) {
        composable(BottomNavigationScreens.Frankendroid.route) {
            //ScaryScreen(ScaryAnimation.Frankendroid)
        }
        composable(BottomNavigationScreens.Pumpkin.route) {
            //ScaryScreen(ScaryAnimation.Pumpkin)
        }
        composable(BottomNavigationScreens.Ghost.route) {
            //ScaryScreen(ScaryAnimation.Ghost)
        }
    }
}

@Composable
private fun SppokyAppBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.route) },
                label = { Text(stringResource(id = screen.resourceId)) },
                selected = currentRoute == screen.route,
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}



@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
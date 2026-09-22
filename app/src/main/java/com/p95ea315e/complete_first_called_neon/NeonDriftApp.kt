package com.p95ea315e.complete_first_called_neon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.p95ea315e.complete_first_called_neon.core.data.SyndicateHelm
import com.p95ea315e.complete_first_called_neon.core.design.NeonColors
import com.p95ea315e.complete_first_called_neon.feature.achievements.AchievementsScreen
import com.p95ea315e.complete_first_called_neon.feature.cars.CarCollectionScreen
import com.p95ea315e.complete_first_called_neon.feature.cars.CarDetailScreen
import com.p95ea315e.complete_first_called_neon.feature.crew.CrewScreen
import com.p95ea315e.complete_first_called_neon.feature.dashboard.DashboardScreen
import com.p95ea315e.complete_first_called_neon.feature.garage.GarageScreen
import com.p95ea315e.complete_first_called_neon.feature.marketplace.MarketplaceScreen
import com.p95ea315e.complete_first_called_neon.feature.races.RaceEventsScreen
import com.p95ea315e.complete_first_called_neon.feature.settings.SettingsScreen
import com.p95ea315e.complete_first_called_neon.feature.splash.SplashScreen
import com.p95ea315e.complete_first_called_neon.feature.more.MoreMenuScreen
import com.p95ea315e.complete_first_called_neon.feature.statistics.StatisticsScreen

private enum class BottomTab(val route: String, val label: String, val icon: ImageVector) {
    HOME("dashboard", "HOME", Icons.Filled.Home),
    CARS("cars", "CARS", Icons.Filled.DirectionsCar),
    RACE("races", "RACE", Icons.Filled.Speed),
    ACHIEVEMENTS("achievements", "AWARDS", Icons.Filled.EmojiEvents),
    MORE("more", "MORE", Icons.Filled.MoreHoriz)
}

private val bottomBarRoutes = setOf(
    "dashboard", "cars", "races", "achievements", "more_menu",
    "garage", "crew", "marketplace", "statistics", "settings"
)

@Composable
fun NeonDriftApp(skipSplash: Boolean = false) {
    val viewModel: SyndicateHelm = viewModel()
    val state by viewModel.state.collectAsState()
    val lastResult by viewModel.lastRaceResult.collectAsState()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in bottomBarRoutes ||
        BottomTab.entries.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                NeonBottomBar(
                    currentRoute = currentRoute,
                    onTabSelected = { tab ->
                        if (tab.route == "more") {
                            navController.navigate("more_menu") {
                                launchSingleTop = true
                            }
                        } else {
                            navController.navigate(tab.route) {
                                popUpTo("dashboard") { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        },
        containerColor = NeonColors.Background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(NeonColors.Background)
                .padding(padding)
        ) {
            NavHost(
                navController = navController,
                startDestination = if (skipSplash) "dashboard" else "splash"
            ) {
                composable("splash") {
                    SplashScreen(onFinished = {
                        navController.navigate("dashboard") {
                            popUpTo("splash") { inclusive = true }
                        }
                    })
                }

                composable("dashboard") {
                    DashboardScreen(
                        state = state,
                        onNavigateToRaces = { navController.navigate("races") },
                        onNavigateToGarage = { navController.navigate("garage") },
                        onNavigateToCars = { navController.navigate("cars") }
                    )
                }

                composable("cars") {
                    CarCollectionScreen(
                        state = state,
                        onCarClick = { carId -> navController.navigate("car_detail/$carId") }
                    )
                }

                composable(
                    route = "car_detail/{carId}",
                    arguments = listOf(navArgument("carId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val carId = backStackEntry.arguments?.getInt("carId") ?: return@composable
                    CarDetailScreen(
                        carId = carId,
                        state = state,
                        onBack = { navController.popBackStack() },
                        onBuyCar = { car -> viewModel.buyCar(car) },
                        onSellCar = { car ->
                            viewModel.sellCar(car)
                            navController.popBackStack()
                        },
                        onSelectCar = { id -> viewModel.selectCar(id) }
                    )
                }

                composable("garage") {
                    GarageScreen(
                        state = state,
                        onUpgradeBuilding = { building -> viewModel.upgradeBuilding(building) }
                    )
                }

                composable("races") {
                    RaceEventsScreen(
                        state = state,
                        lastResult = lastResult,
                        onRace = { event -> viewModel.race(event) },
                        onDismissResult = { viewModel.dismissRaceResult() }
                    )
                }

                composable("achievements") {
                    AchievementsScreen(state = state)
                }

                composable("more_menu") {
                    MoreMenuScreen(
                        onNavigateTo = { route -> navController.navigate(route) }
                    )
                }

                composable("crew") {
                    CrewScreen(
                        state = state,
                        onHire = { id -> viewModel.hireCrew(id) },
                        onFire = { id -> viewModel.fireCrew(id) }
                    )
                }

                composable("marketplace") {
                    MarketplaceScreen(
                        state = state,
                        onBuyCar = { car -> viewModel.buyCar(car) },
                        onSellCar = { car -> viewModel.sellCar(car) },
                        onBuyPart = { id -> viewModel.buyPart(id) },
                        onSellPart = { id -> viewModel.sellPart(id) }
                    )
                }

                composable("statistics") {
                    StatisticsScreen(state = state)
                }

                composable("settings") {
                    SettingsScreen(
                        state = state,
                        onResetProgress = { viewModel.resetProgress() }
                    )
                }
            }
        }
    }
}

@Composable
private fun NeonBottomBar(
    currentRoute: String?,
    onTabSelected: (BottomTab) -> Unit
) {
    NavigationBar(
        containerColor = NeonColors.SurfaceDark,
        tonalElevation = 0.dp
    ) {
        BottomTab.entries.forEach { tab ->
            val selected = when (tab) {
                BottomTab.MORE -> currentRoute == "more_menu" || currentRoute in setOf("crew", "marketplace", "statistics", "settings", "garage")
                else -> currentRoute == tab.route
            }
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.label,
                        tint = if (selected) neonTabColor(tab) else NeonColors.TextMuted
                    )
                },
                label = {
                    Text(
                        tab.label,
                        fontSize = 9.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected) neonTabColor(tab) else NeonColors.TextMuted
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = neonTabColor(tab),
                    indicatorColor = neonTabColor(tab).copy(alpha = 0.15f)
                )
            )
        }
    }
}

private fun neonTabColor(tab: BottomTab): Color = when (tab) {
    BottomTab.HOME -> NeonColors.NeonCyan
    BottomTab.CARS -> NeonColors.NeonPink
    BottomTab.RACE -> NeonColors.NeonPurple
    BottomTab.ACHIEVEMENTS -> NeonColors.NeonGold
    BottomTab.MORE -> NeonColors.ElectricBlue
}

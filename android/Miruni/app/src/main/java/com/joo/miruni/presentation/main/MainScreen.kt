1package com.joo.miruni.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.joo.miruni.R
import com.joo.miruni.presentation.BottomNavItem
import com.joo.miruni.presentation.Screen
import com.joo.miruni.presentation.addTask.addSchedule.AddScheduleScreen
import com.joo.miruni.presentation.addTask.addTodo.AddTodoScreen
import com.joo.miruni.presentation.calendar.CalendarScreen
import com.joo.miruni.presentation.detail.detailSchedule.DetailScheduleScreen
import com.joo.miruni.presentation.detail.detailTodo.DetailTodoScreen
import com.joo.miruni.presentation.home.HomeScreen
import com.joo.miruni.presentation.overdue.OverdueScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
) {

    val isCompletedViewChecked = mainViewModel.settingObserveCompleteVisibility.collectAsStateWithLifecycle()
    val isActiveUnlockScreenChecked = mainViewModel.settingObserveUnlockState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBars = currentRoute in listOf(Screen.Home.route, Screen.Overdue.route, Screen.Calendar.route)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // 메뉴
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // 앱 제목
                        Row {
                            Text(
                                text = stringResource(R.string.app_title),
                                modifier = Modifier.padding(vertical = 8.dp),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // 메뉴 항목 1
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.setting_completed_items),
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .weight(1f),
                                fontSize = 16.sp,
                            )

                            // 스위치
                            Switch(
                                modifier = Modifier.padding(end = 8.dp),
                                checked = isCompletedViewChecked.value,
                                onCheckedChange = {
                                    mainViewModel.setCompletedItemsVisibility()
                                },
                                colors = SwitchDefaults.colors(
                                    checkedIconColor = Color.White,
                                    checkedTrackColor = Color(0xFF35C759),
                                    checkedBorderColor = Color(0xFF35C759),
                                    checkedThumbColor = Color.White,
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = Color.LightGray,
                                    uncheckedBorderColor = Color.White,
                                ),
                            )
                        }

                        // 메뉴 항목 2
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.setting_lock_screen),
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .weight(1f),
                                fontSize = 16.sp,
                            )

                            // 스위치
                            Switch(
                                modifier = Modifier.padding(end = 8.dp),
                                checked = isActiveUnlockScreenChecked.value,
                                onCheckedChange = {
                                    mainViewModel.setActiveUnlockScreen()
                                },
                                colors = SwitchDefaults.colors(
                                    checkedIconColor = Color.White,
                                    checkedTrackColor = Color(0xFF35C759),
                                    checkedBorderColor = Color(0xFF35C759),
                                    checkedThumbColor = Color.White,
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = Color.LightGray,
                                    uncheckedBorderColor = Color.White,
                                ),
                            )
                        }
                    }
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                if (showBars) {
                    Column {
                        TopAppBar(title = { Text(text = "") }, navigationIcon = {
                            Box(modifier = Modifier
                                .padding(16.dp)
                                .testTag("main_btn_menu")
                                .clickable(indication = null,
                                    interactionSource = remember { MutableInteractionSource() }) {
                                    scope.launch { drawerState.open() }
                                }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_menu),
                                    contentDescription = stringResource(R.string.cd_menu),
                                )
                            }
                        }, colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White
                        )
                        )
                        HorizontalDivider(color = Color.Gray, thickness = 0.5.dp)
                    }
                }
            },
            bottomBar = {
                if (showBars) {
                    BottomNavigationBar(
                        navController, mainViewModel.bottomNavItems
                    )
                }
            },
            content = { contentPadding ->
                Box(
                    modifier = Modifier.padding(contentPadding)
                ) {
                    NavigationHost(navController)
                }
            },
            containerColor = Color.White,
        )
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavItem>,
) {
    val currentDestination = navController.currentDestination?.route

    Column {
        HorizontalDivider(
            thickness = 0.5.dp, color = Color.Gray
        )

        NavigationBar(
            containerColor = Color.White, contentColor = Color.White,
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(item.iconResId), contentDescription = stringResource(item.labelResId)
                        )
                    },
                    label = { Text(stringResource(item.labelResId)) },
                    selected = currentDestination == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.White,
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color.Black,
                        unselectedTextColor = Color.Gray,
                    ),
                    modifier = Modifier
                        .testTag("main_nav_${item.screen.route}")
                )

            }
        }
    }
}


@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) { HomeScreen(navController = navController) }
        composable(Screen.Overdue.route) { OverdueScreen(navController = navController) }
        composable(Screen.Calendar.route) { CalendarScreen(navController = navController) }
        composable(
            Screen.AddTodo.route,
            arguments = listOf(navArgument("selectDate") { type = NavType.StringType; defaultValue = "" })
        ) { backStackEntry ->
            val selectDate = backStackEntry.arguments?.getString("selectDate")
            AddTodoScreen(initialDateString = selectDate, navController = navController)
        }
        composable(
            Screen.AddSchedule.route,
            arguments = listOf(navArgument("selectDate") { type = NavType.StringType; defaultValue = "" })
        ) { backStackEntry ->
            val selectDate = backStackEntry.arguments?.getString("selectDate")
            AddScheduleScreen(selectDate = selectDate, navController = navController)
        }
        composable(
            Screen.DetailTodo.route,
            arguments = listOf(navArgument("todoId") { type = NavType.LongType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getLong("todoId") ?: -1L
            DetailTodoScreen(todoId = todoId)
        }
        composable(
            Screen.DetailSchedule.route,
            arguments = listOf(navArgument("scheduleId") { type = NavType.LongType })
        ) { backStackEntry ->
            val scheduleId = backStackEntry.arguments?.getLong("scheduleId") ?: -1L
            DetailScheduleScreen(scheduleId = scheduleId)
        }
    }
}

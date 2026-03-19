package com.joo.miruni.presentation.home

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.joo.miruni.R
import com.joo.miruni.presentation.widget.BasicDialog
import com.joo.miruni.presentation.widget.DialogMod
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController? = null,
) {
    val context = LocalContext.current

    /*
    * State Flow
    * */
    val thingsToDoItems by homeViewModel.thingsTodoItems.collectAsStateWithLifecycle()
    val scheduleItems by homeViewModel.scheduleItems.collectAsStateWithLifecycle()
    val deletedItems by homeViewModel.deletedItems.collectAsStateWithLifecycle()

    val selectDate by homeViewModel.selectDate.collectAsStateWithLifecycle()

    val isTodoListLoading by homeViewModel.isTodoListLoading.collectAsStateWithLifecycle()
    val isScheduleListLoading by homeViewModel.isScheduleListLoading.collectAsStateWithLifecycle()
    val isCompletedViewChecked =
        homeViewModel.settingObserveCompleteVisibility.collectAsStateWithLifecycle()
    val isFutureDate by homeViewModel.isFutureDate.collectAsStateWithLifecycle()

    // FAB 메뉴
    var isAddMenuExpanded by remember { mutableStateOf(false) }

    // 한번 초기화가 되었는지 판단 변수
    var initialLoad by remember { mutableStateOf(true) }

    // 일정 페이지 상태
    val pageCount = (scheduleItems.size + 2) / 3
    val schedulePagerState = rememberPagerState(
        pageCount = {
            pageCount
        })

    // 무한 스크롤
    val lazyListState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 헤더
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 왼쪽 버튼
                IconButton(
                    onClick = {
                        if (isFutureDate) {
                            homeViewModel.changeDate(DateChange.LEFT)
                            initialLoad = false
                        }
                    },
                    modifier = Modifier
                        .alpha(if (isFutureDate) 1f else 0.25f)
                        .testTag("home_btn_prev_date")
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_left),
                        contentDescription = stringResource(R.string.cd_previous_date)
                    )
                }


                // 중앙 텍스트 및 아이콘
                Row(
                    modifier = Modifier.weight(0.9f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = homeViewModel.formatSelectedDate(selectDate),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_weather),
                        contentDescription = stringResource(R.string.cd_weather),
                        modifier = Modifier.size(24.dp)
                    )
                }

                // 오른쪽 버튼
                IconButton(
                    onClick = {
                        homeViewModel.changeDate(DateChange.RIGHT)
                        initialLoad = false
                    },
                    modifier = Modifier.testTag("home_btn_next_date")
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_right),
                        contentDescription = stringResource(R.string.cd_next_date)
                    )
                }
            }

            // 일정
            if (scheduleItems.isNotEmpty()) {
                HorizontalPager(
                    state = schedulePagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp)
                ) { page ->
                    // 완료된 항목 필터 리스트
                    val filteredScheduleItems = scheduleItems.filter {
                        isCompletedViewChecked.value || !it.isComplete
                    }

                    val startIndex = page * 3
                    val endIndex = minOf(startIndex + 3, filteredScheduleItems.size)

                    if (startIndex < filteredScheduleItems.size) {
                        val scheduleGroup = filteredScheduleItems.subList(startIndex, endIndex)

                        Column(
                            modifier = Modifier
                                .height(60.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            for (i in scheduleGroup.indices step 3) {
                                Row {
                                    for (j in i until minOf(i + 3, scheduleGroup.size)) {
                                        ScheduleItem(
                                            context = context,
                                            schedule = scheduleGroup[j],
                                            onLongClicked = {
                                                homeViewModel.togglePinStatus(scheduleGroup[j].id)
                                            },
                                            navController = navController,
                                        )
                                    }
                                    // 로딩바
                                    if (isScheduleListLoading && page == pageCount - 1) {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .padding(16.dp),
                                            color = colorResource(R.color.ios_gray)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // 페이지가 끝에 도달했을 때 추가 데이터 로드
                    LaunchedEffect(schedulePagerState.currentPage) {
                        if (page == pageCount - 1) {
                            homeViewModel.loadMoreScheduleData()
                        }
                    }
                }
            }


            // 할 일
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                if (thingsToDoItems.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.home_no_todos),
                                color = colorResource(R.color.ios_gray),
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            )
                        }
                    }
                } else {
                    items(thingsToDoItems.size) { index ->
                        val thingsToDo = thingsToDoItems[index]

                        val isDelete = deletedItems.contains(thingsToDo.id)
                        val isExpanded = homeViewModel.expandedItems.value.contains(thingsToDo.id)
                        val isOverdue =
                            LocalDateTime.now().isAfter(thingsToDo.deadline) || LocalDateTime.now()
                                .isEqual(thingsToDo.deadline)

                        if (!isOverdue) {
                            if (isCompletedViewChecked.value || !thingsToDo.isCompleted) {
                                ThingsToDoItem(
                                    context = context,
                                    thingsToDo = thingsToDo,
                                    onClicked = {
                                        homeViewModel.toggleItemExpansion(thingsToDo.id)
                                    },
                                    onClickedShowDetail = {
                                        // 상세보기 화면으로 넘어가기
                                        navController?.navigate("detailTodo/${thingsToDo.id}")
                                        homeViewModel.collapseAllItems()
                                    },
                                    onClickedDelay = {
                                        // 미루기 클릭 시
                                        homeViewModel.delayTodoItem(thingsToDo)
                                    },
                                    onDialogConfirmed = { dialogMod ->
                                        when (dialogMod) {
                                            DialogMod.TODO_COMPLETE -> {
                                                homeViewModel.completeTask(thingsToDo.id)
                                                homeViewModel.collapseAllItems()
                                            }

                                            DialogMod.TODO_DELETE -> {
                                                homeViewModel.deleteTaskItem(thingsToDo.id)
                                                homeViewModel.collapseAllItems()
                                            }

                                            DialogMod.TODO_CANCEL_COMPLETE -> {
                                                homeViewModel.completeCancelTaskItem(thingsToDo.id)
                                                homeViewModel.collapseAllItems()
                                            }

                                            else -> {
                                                homeViewModel.collapseAllItems()
                                            }
                                        }
                                    },
                                    onLongClicked = {
                                        homeViewModel.togglePinStatus(thingsToDo.id)
                                    },
                                    isDelete = isDelete,
                                    isExpanded = isExpanded,
                                )
                            }
                        }
                    }
                }

                // 로딩 인디케이터
                if (isTodoListLoading) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(16.dp),
                                color = colorResource(R.color.ios_gray)
                            )
                        }
                    }
                }
            }
        }

        // 플로팅 버튼 + 메뉴
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // FAB
            FloatingActionButton(
                onClick = { isAddMenuExpanded = !isAddMenuExpanded },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
                    .testTag("home_fab_add"),
                shape = CircleShape,
                containerColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
            ) {
                Icon(
                    modifier = Modifier.size(68.dp),
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = stringResource(R.string.cd_add_item),
                    tint = Color.Black,
                )
                // 메뉴
                DropdownMenu(
                    expanded = isAddMenuExpanded,
                    onDismissRequest = { isAddMenuExpanded = false },
                    offset = DpOffset(x = (-72).dp, y = (48).dp),
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp,
                    shadowElevation = 0.dp,
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = colorResource(R.color.gray_menu),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        tonalElevation = 4.dp,
                        shadowElevation = 4.dp,
                    ) {
                        Column {
                            Text(
                                text = stringResource(R.string.todo),
                                modifier = Modifier
                                    .clickable(
                                        indication = ripple(
                                            bounded = true,
                                            color = colorResource(R.color.ios_gray),
                                        ),
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) {
                                        // 할 일 추가
                                        navController?.navigate("addTodo")
                                        isAddMenuExpanded = false
                                        homeViewModel.collapseAllItems()
                                    }
                                    .padding(16.dp)
                                    .defaultMinSize(60.dp)
                                    .testTag("home_menu_add_todo")
                            )
                            HorizontalDivider(
                                thickness = 0.5.dp,
                                color = Color.Black.copy(alpha = 0.2f)
                            )
                            Text(
                                text = stringResource(R.string.schedule),
                                modifier = Modifier
                                    .clickable(
                                        indication = ripple(
                                            bounded = true,
                                            color = colorResource(R.color.ios_gray),
                                        ),
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) {
                                        // 일정 추가
                                        navController?.navigate("addSchedule")
                                        isAddMenuExpanded = false
                                        homeViewModel.collapseAllItems()
                                    }
                                    .padding(16.dp)
                                    .defaultMinSize(60.dp)
                                    .testTag("home_menu_add_schedule")
                            )
                        }
                    }
                }
            }
        }

    }

    // 초기 로드 상태 업데이트
    LaunchedEffect(thingsToDoItems) {
        if (thingsToDoItems.isNotEmpty()) {
            initialLoad = false
        }
    }

    // 화면 재진입
    LaunchedEffect(Unit) {
        homeViewModel.refreshScreen()
    }
}

// 일정 Item
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduleItem(
    context: Context,
    schedule: Schedule,
    onLongClicked: () -> Unit,
    isClickable: Boolean = true,
    navController: NavHostController? = null,
) {
    // 햅틱
    val haptics = LocalHapticFeedback.current

    // 화면 크기
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val isDDayAnimation by rememberInfiniteTransition(label = "D-Day").animateFloat(
        initialValue = 1f,
        targetValue = 0.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "D-Day"
    )

    Card(
        modifier = Modifier
            .widthIn(max = (screenWidth / 3).dp, min = (screenWidth / 3).dp)
            .heightIn(max = 80.dp, min = 80.dp)
            .padding(
                horizontal = 6.dp,
                vertical = 4.dp,
            )
            .testTag("home_schedule_item_${schedule.id}")
            .combinedClickable(
                indication = ripple(
                    bounded = true,
                    color = colorResource(R.color.ios_gray),
                ),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    // 클릭 시
                    if (isClickable) {
                        navController?.navigate("detailSchedule/${schedule.id}")
                    }
                },
                onLongClick = {
                    // 길게 클릭 시
                    if (isClickable) {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        onLongClicked()
                    }
                },
            ),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!schedule.isComplete) {
                colorResource(id = R.color.gray_menu)
            } else {
                colorResource(id = R.color.ios_complete_gray)
            },
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // D-Day
                Text(
                    modifier = Modifier
                        .alpha(if (schedule.daysBefore == 0) isDDayAnimation else 1f),
                    text = if (schedule.daysBefore == 0) {
                        "D-DAY"
                    } else {
                        "D-${schedule.daysBefore}"
                    },
                    fontWeight = if (schedule.daysBefore == 0) {
                        FontWeight.Bold
                    } else {
                        FontWeight.SemiBold
                    },
                )

                // 일정 제목
                Text(
                    text = schedule.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (schedule.isPinned) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pin_inversion),
                    contentDescription = stringResource(R.string.cd_pin),
                    modifier = Modifier.size(8.dp)
                )
            }
        }
    }
}

// 할 일 Item
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ThingsToDoItem(
    context: Context,
    thingsToDo: ThingsTodo,
    isDelete: Boolean,
    isExpanded: Boolean,
    onClicked: () -> Unit,
    onLongClicked: () -> Unit,
    onClickedShowDetail: () -> Unit,
    onClickedDelay: () -> Unit,
    onDialogConfirmed: (DialogMod) -> Unit,
    isClickable: Boolean = true,
) {
    // 햅틱
    val haptics = LocalHapticFeedback.current

    var isOpenThingsTodoMenu by remember { mutableStateOf(false) }        // MenuIcon 터치 여부

    var showDialog by remember { mutableStateOf(false) }                  // dialog 보여짐 여부
    var dialogMod by remember { mutableStateOf(DialogMod.TODO_DELETE) }         // dialog mod

    val isComplete = thingsToDo.isCompleted
    val isPinned = thingsToDo.isPinned
    val isOverdue = LocalDateTime.now().isAfter(thingsToDo.deadline) || LocalDateTime.now()
        .isEqual(thingsToDo.deadline)

    /*
    * 애니매이션
    * */
    val flashAnimation by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    AnimatedVisibility(
        visible = !isDelete,
        enter = fadeIn(animationSpec = tween(durationMillis = 0)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .testTag("home_todo_item_${thingsToDo.id}")
                    .combinedClickable(
                        indication = ripple(
                            bounded = true,
                            color = colorResource(R.color.ios_gray),
                        ),
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            // 클릭 시
                            if (isClickable) {
                                onClicked()
                            }
                        },
                        onLongClick = {
                            // 길게 클릭 시
                            if (isClickable) {
                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                onLongClicked()
                            }
                        },
                    ),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isComplete) {
                        colorResource(R.color.ios_complete_gray)
                    } else {
                        colorResource(R.color.gray_menu)
                    },
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            )
                            .animateContentSize()
                    ) {
                        // 제목, 남은 기간, 더보기
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 제목
                            Text(
                                text = thingsToDo.title,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .weight(0.8f)
                                    .padding(start = 2.dp)
                            )

                            // 남은 시간
                            Text(
                                text = run {
                                    val duration =
                                        Duration.between(LocalDateTime.now(), thingsToDo.deadline)

                                    val minutesRemaining = duration.toMinutes()
                                    val hoursRemaining = duration.toHours()
                                    val daysRemaining = duration.toDays()

                                    when {
                                        minutesRemaining <= 0 -> stringResource(R.string.deadline_expired)
                                        minutesRemaining <= 60 -> stringResource(R.string.minutes_later, minutesRemaining.toInt())
                                        hoursRemaining <= 24 -> stringResource(R.string.hours_later, hoursRemaining.toInt())
                                        daysRemaining <= 7 -> stringResource(R.string.days_later, daysRemaining.toInt())
                                        else -> thingsToDo.deadline.format(
                                            DateTimeFormatter.ofPattern(
                                                "yyyy.MM.dd"
                                            )
                                        )
                                    }
                                },
                                fontSize = 12.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 4.dp)
                            )

                            // 메뉴 아이콘
                            Box(
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .semantics { role = Role.Button }
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) {
                                        if (isClickable) {
                                            isOpenThingsTodoMenu = !isOpenThingsTodoMenu
                                        }
                                    }
                                    .background(Color.Transparent)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_more),
                                    contentDescription = stringResource(R.string.cd_see_more),
                                    modifier = Modifier.size(12.dp),
                                    tint = Color.Black
                                )

                                // 메뉴
                                DropdownMenu(
                                    expanded = isOpenThingsTodoMenu,
                                    onDismissRequest = { isOpenThingsTodoMenu = false },
                                    offset = DpOffset(x = 0.dp, y = (-24).dp),
                                    containerColor = Color.Transparent,
                                    tonalElevation = 0.dp,
                                    shadowElevation = 0.dp,
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = colorResource(R.color.gray_menu),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        tonalElevation = 6.dp,
                                        shadowElevation = 6.dp,
                                    ) {

                                        // 메뉴 아이템
                                        Column {

                                            // 상세보기 메뉴
                                            Text(
                                                text = stringResource(R.string.detail),
                                                modifier = Modifier
                                                    .clickable(
                                                        indication = ripple(
                                                            bounded = true,
                                                            color = colorResource(R.color.ios_gray),
                                                        ),
                                                        interactionSource = remember { MutableInteractionSource() }
                                                    ) {
                                                        onClickedShowDetail()
                                                        isOpenThingsTodoMenu =
                                                            !isOpenThingsTodoMenu
                                                    }
                                                    .padding(16.dp)
                                                    .defaultMinSize(60.dp)
                                            )
                                            HorizontalDivider(
                                                thickness = 0.5.dp,
                                                color = Color.Black.copy(alpha = 0.2f)
                                            )

                                            // 미루기 메뉴
                                            Text(
                                                text = stringResource(R.string.delay),
                                                modifier = Modifier
                                                    .clickable(
                                                        indication = ripple(
                                                            bounded = true,
                                                            color = colorResource(R.color.ios_gray),
                                                        ),
                                                        interactionSource = remember { MutableInteractionSource() }
                                                    ) {
                                                        onClickedDelay()
                                                        isOpenThingsTodoMenu =
                                                            !isOpenThingsTodoMenu
                                                    }
                                                    .padding(16.dp)
                                                    .defaultMinSize(60.dp),
                                                color = Color.Red
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // 설명칸
                        if (!thingsToDo.description.isNullOrEmpty()) {
                            Text(
                                text = thingsToDo.description,
                                fontSize = 12.sp,
                                color = Color.DarkGray,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 8.dp, bottom = 10.dp)
                            )
                        }

                        // 완료 날짜 표시
                        if (isComplete) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp, end = 8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = run {
                                        val currentYear = LocalDateTime.now().year
                                        val completeDateStr = thingsToDo.completeDate?.let { date ->
                                            val formatter = if (date.year == currentYear) {
                                                DateTimeFormatter.ofPattern("M월 d일 a h시 m분")
                                            } else {
                                                DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분")
                                            }
                                            date.format(formatter)
                                        } ?: "(알 수 없음)"

                                        stringResource(R.string.completed_at, completeDateStr)
                                    },
                                    fontSize = 10.sp
                                )

                            }
                        }

                        // 기한이 지난
                        if (isOverdue && !isComplete) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp, end = 8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = run {
                                        val expiredDateStr = thingsToDo.deadline.let { date ->
                                            val formatter =
                                                if (date.year == LocalDateTime.now().year) {
                                                    DateTimeFormatter.ofPattern("M월 d일 a h시 m분")
                                                } else {
                                                    DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분")
                                                }
                                            date.format(formatter)
                                        } ?: "(알 수 없음)"

                                        stringResource(R.string.expired_at, expiredDateStr)
                                    },
                                    fontSize = 10.sp
                                )
                            }
                        }

                        // 추가 정보와 구분선
                        if (isExpanded) {
                            HorizontalDivider(
                                thickness = 0.5.dp, color = Color.Gray.copy(alpha = 0.5f),
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // 삭제 텍스트
                                Column(
                                    modifier = Modifier
                                        .background(Color.Transparent)
                                        .weight(1f)
                                        .semantics { role = Role.Button }
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) {
                                            dialogMod = DialogMod.TODO_DELETE
                                            showDialog = true
                                        }
                                        .padding(8.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = stringResource(R.string.delete),
                                        color = Color.Red,
                                        textAlign = TextAlign.Center,
                                    )
                                }

                                // 구분선
                                Spacer(modifier = Modifier.width(12.dp))
                                VerticalDivider(
                                    thickness = 0.8.dp,
                                    color = Color.Gray.copy(alpha = 0.5f),
                                    modifier = Modifier.height(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))

                                // 완료 / 완료 취소 텍스트
                                Column(
                                    modifier = Modifier
                                        .background(Color.Transparent)
                                        .weight(1f)
                                        .semantics { role = Role.Button }
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) {
                                            dialogMod = if (isComplete) {
                                                DialogMod.TODO_CANCEL_COMPLETE
                                            } else {
                                                DialogMod.TODO_COMPLETE

                                            }
                                            showDialog = true
                                        }
                                        .padding(8.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = if (isComplete) stringResource(R.string.dialog_cancel_complete_button) else stringResource(R.string.complete),
                                        color = colorResource(R.color.ios_blue),
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }

                        }

                        // 다이얼로그
                        BasicDialog(
                            dialogType = dialogMod,
                            showDialog = showDialog,
                            onDismiss = {
                                showDialog = false
                            },
                            onCancel = {
                                showDialog = false
                            },
                            onConfirmed = {
                                onDialogConfirmed(dialogMod)
                            },
                            title = thingsToDo.title,
                        )
                    }
                }
            }

            // 알림 중요도 표시등
            if (isComplete) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = stringResource(R.string.cd_complete),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.Green,
                                shape = RoundedCornerShape(90.dp)
                            )
                            .padding(2.dp),
                        colorFilter = ColorFilter.tint(Color.White),
                    )
                }
            } else if (isOverdue) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.TopStart)
                        .background(Color.Transparent)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = stringResource(R.string.cd_overdue),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(90.dp)
                            )
                            .size(28.dp),
                        colorFilter = ColorFilter.tint(Color.Red),
                    )
                }
            } else {
                val now = LocalDateTime.now()
                val minutesRemaining = ChronoUnit.MINUTES.between(now, thingsToDo.deadline)

                // 남은 시간에 따른 색
                val backgroundColor = when {
                    minutesRemaining < 720 -> Color.Red.copy(alpha = flashAnimation)    // 12시간 이내
                    minutesRemaining < 1440 -> Color.Red                                // 24시간 이내
                    minutesRemaining < 2880 -> colorResource(R.color.orange)            // 2일 이내
                    minutesRemaining < 4320 -> colorResource(R.color.yellow)            // 3일 이내
                    else -> Color.Green                                                 // 7일 이내
                }

                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = if (!isPinned) {
                                backgroundColor
                            } else {
                                Color.Transparent
                            },
                            shape = RoundedCornerShape(90.dp)
                        )
                        .align(Alignment.TopStart)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_pin_inversion),
                        contentDescription = stringResource(R.string.cd_pin),
                        colorFilter = if (isPinned) {
                            ColorFilter.tint(backgroundColor)
                        } else {
                            ColorFilter.tint(Color.Transparent)
                        },
                    )
                }
            }
        }
    }
}



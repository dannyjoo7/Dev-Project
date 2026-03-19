package com.joo.miruni.presentation.detail.detailTodo

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.material.timepicker.TimeFormat
import com.joo.miruni.R
import com.joo.miruni.presentation.widget.AlarmDisplayDatePicker
import com.joo.miruni.presentation.widget.BasicDialog
import com.joo.miruni.presentation.widget.DialogMod
import com.joo.miruni.presentation.widget.Time
import com.joo.miruni.presentation.widget.WheelTimePicker
import java.time.LocalDate
import java.time.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTodoScreen(
    detailTodoViewModel: DetailTodoViewModel = hiltViewModel(),
    todoId: Long = -1L,
    navController: NavHostController? = null,
) {
    // 현재 컨택스트
    val context = LocalContext.current

    // 키보드 컨트롤러
    val keyboardController = LocalSoftwareKeyboardController.current

    // 스크롤 상태
    val scrollState = rememberScrollState()

    /*
    * Live Data
    *  */
    val todoItem by detailTodoViewModel.todoItem.collectAsStateWithLifecycle()

    val todoText by detailTodoViewModel.todoText.collectAsStateWithLifecycle()
    val descriptionText by detailTodoViewModel.descriptionText.collectAsStateWithLifecycle()

    val isModified by detailTodoViewModel.isModified.collectAsStateWithLifecycle()

    val showDatePicker by detailTodoViewModel.showDatePicker.collectAsStateWithLifecycle()
    val showTimePicker by detailTodoViewModel.showTimePicker.collectAsStateWithLifecycle()
    val showAlarmDisplayStartDatePicker by detailTodoViewModel.showAlarmDisplayStartDatePicker.collectAsStateWithLifecycle()

    val selectDate by detailTodoViewModel.selectedDate.collectAsStateWithLifecycle()
    val selectTime by detailTodoViewModel.selectedTime.collectAsStateWithLifecycle()
    val selectedAlarmDisplayDate by detailTodoViewModel.selectedAlarmDisplayDate.collectAsStateWithLifecycle()

    val isTodoTextEmpty by detailTodoViewModel.isTodoTextEmpty.collectAsStateWithLifecycle()
    val isTodoAddedSuccess by detailTodoViewModel.isTodoUpdate.collectAsStateWithLifecycle()

    /*
    * UI
    * */
    var showDialog by remember { mutableStateOf(false) }               // dialog 보여짐 여부
    var dialogMod by remember { mutableStateOf(DialogMod.TODO_DELETE) }      // dialog mod

    /*
    * 애니매이션
    * */
    val todoTextColor = if (isTodoTextEmpty) Color.Red else colorResource(R.color.ios_gray)
    val todoShakeOffset = remember { Animatable(0f) }

    // keyFrames 단위로 흔들기 애니매이션
    val shakeKeyframes: AnimationSpec<Float> = keyframes {
        durationMillis = 400
        val easing = FastOutLinearInEasing

        for (i in 1..8) {
            val x = when (i % 3) {
                0 -> 4f
                1 -> -4f
                else -> 0f
            }
            x at durationMillis / 10 * i using easing
        }
    }

    LaunchedEffect(todoId) {
        if (todoId != -1L) {
            detailTodoViewModel.loadTodoDetails(todoId)
        }
    }

    // 비었을 시 애니메이션 실행
    LaunchedEffect(isTodoTextEmpty) {
        if (isTodoTextEmpty) {
            todoShakeOffset.animateTo(
                targetValue = 0f,
                animationSpec = shakeKeyframes
            )
            detailTodoViewModel.finishAnimation()
        }
    }


    // Todo추가 성공 시 해당 액티비티 종료
    LaunchedEffect(isTodoAddedSuccess) {
        if (isTodoAddedSuccess) {
            navController?.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.todo),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = stringResource(R.string.edit),
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    enabled = isModified
                                ) {
                                    detailTodoViewModel.updateTodoItem()
                                },
                            color = if (isModified) colorResource(id = R.color.ios_blue) else Color.Transparent,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController?.popBackStack()
                        },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.cd_back),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            if (!isModified) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 18.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // 삭제 아이콘
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                dialogMod = DialogMod.TODO_DELETE
                                showDialog = true
                            },
                        painter = painterResource(id = R.drawable.ic_trash_can),
                        contentDescription = stringResource(R.string.cd_delete_todo),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.ios_red)),
                    )
                    // 완료 아이콘
                    Image(
                        modifier = Modifier
                            .size(30.dp)
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                if (todoItem == null) {
                                    showDialog = false
                                } else {
                                    dialogMod = if (todoItem?.isComplete == true) {
                                        DialogMod.TODO_CANCEL_COMPLETE
                                    } else {
                                        DialogMod.TODO_COMPLETE
                                    }
                                    showDialog = true
                                }
                            },
                        painter = painterResource(
                            id = if (todoItem?.isComplete == true) {
                                R.drawable.ic_calendar_uncheck
                            } else {
                                R.drawable.ic_calendar_check
                            }
                        ),
                        contentDescription = stringResource(R.string.cd_complete_todo),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.ios_blue)),
                    )
                }
            }
        },
        containerColor = Color.White
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // 할 일 입력창
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 60.dp)
                            .offset {
                                IntOffset(
                                    x = todoShakeOffset.value
                                        .toDp()
                                        .toPx()
                                        .toInt(),
                                    y = 0
                                )
                            }
                    ) {
                        Text(
                            text = stringResource(R.string.todo),
                            modifier = Modifier
                                .padding(end = 34.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        TextField(
                            value = todoText,
                            onValueChange = {
                                detailTodoViewModel.updateTodoText(it)
                            },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.todo),
                                    fontSize = 16.sp,
                                    color = todoTextColor
                                )

                            },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = colorResource(R.color.ios_blue),
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                        )
                    }
                    HorizontalDivider(color = Color.Gray, thickness = 0.5.dp)
                }

                // 세부사항 입력창
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 60.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.description),
                            modifier = Modifier
                                .padding(end = 8.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        TextField(
                            value = descriptionText,
                            onValueChange = {
                                detailTodoViewModel.updateDescriptionText(it)
                            },
                            singleLine = false,
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.description),
                                    fontSize = 16.sp,
                                    color = colorResource(id = R.color.ios_gray)
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = colorResource(R.color.ios_blue),
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 56.dp),
                        )
                    }
                    HorizontalDivider(color = Color.Gray, thickness = 0.5.dp)
                }

                // 마감일
                Column(
                    modifier = Modifier
                        .animateContentSize(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 60.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.deadline),
                            modifier = Modifier
                                .padding(end = 38.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )

                        // 날짜 선택 버튼
                        Button(
                            onClick = {
                                detailTodoViewModel.clickedDatePickerBtn()
                                keyboardController?.hide()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(
                                vertical = 2.dp,
                                horizontal = 8.dp
                            ),
                        ) {
                            Text(
                                text = detailTodoViewModel.formatSelectedDate(
                                    selectDate ?: LocalDate.now()
                                ),
                                color = colorResource(id = R.color.ios_blue),
                                fontSize = 16.sp,
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))

                        // 시간 선택 버튼
                        Button(
                            onClick = {
                                detailTodoViewModel.clickedTimePickerBtn()
                                keyboardController?.hide()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(
                                vertical = 2.dp,
                                horizontal = 8.dp
                            ),
                        ) {
                            Text(
                                text = detailTodoViewModel.formatLocalTimeToString(
                                    selectTime ?: LocalTime.now()
                                ),
                                color = colorResource(id = R.color.ios_blue),
                                fontSize = 16.sp,
                            )
                        }
                    }
                    HorizontalDivider(color = Color.Gray, thickness = 0.5.dp)

                    /*
                    * 날짜 선택기 & 시간 선택기
                    * */
                    Box {
                        // 날짜 선택기
                        androidx.compose.animation.AnimatedVisibility(
                            visible = showDatePicker,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                        ) {
                            DatePicker(
                                context = context,
                                detailTodoViewModel = detailTodoViewModel,
                                onDateSelected = { date -> detailTodoViewModel.selectDate(date) },
                                onMonthChanged = { month -> detailTodoViewModel.changeMonth(month) },
                            )
                        }

                        // 시간 선택기
                        androidx.compose.animation.AnimatedVisibility(
                            visible = showTimePicker,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                            ) {
                                WheelTimePicker(
                                    offset = 2,
                                    selectorEffectEnabled = true,
                                    timeFormat = TimeFormat.CLOCK_12H,
                                    startTime = detailTodoViewModel.convertLocalTimeToTime()
                                        ?: Time(12, 0, "오전"),
                                    textSize = 19,
                                    onTimeChanged = { hour, minute, format ->
                                        detailTodoViewModel.updateSelectedTime(
                                            hour,
                                            minute,
                                            format ?: "오전"
                                        )
                                    },
                                    darkModeEnabled = false
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        detailTodoViewModel.clickedTimePickerBtn()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF007AFF),
                                        contentColor = Color.White,
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .padding(horizontal = 24.dp)
                                        .fillMaxWidth() // 버튼을 전체 너비로 설정
                                ) {
                                    Text(
                                        text = stringResource(R.string.complete),
                                        textAlign = TextAlign.Center,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                // 알림 표시일 입력창
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 60.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.alarm_display_start_date),
                            modifier = Modifier
                                .padding(end = 16.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )

                        Text(
                            text = "${selectedAlarmDisplayDate.amount ?: 1}${selectedAlarmDisplayDate.unit ?: "일"} 전",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable(
                                    onClick = {
                                        detailTodoViewModel.clickedAlarmDisplayStartDateText()
                                        keyboardController?.hide()
                                    }),
                            fontSize = 16.sp,
                            color = colorResource(R.color.ios_blue),

                            )
                    }
                    HorizontalDivider(color = Color.Gray, thickness = 0.5.dp)

                    /*
                    * 알림 표시 시작일
                    * */
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // 알림 표시 시작일 선택기
                        androidx.compose.animation.AnimatedVisibility(
                            visible = showAlarmDisplayStartDatePicker,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier
                                .align(Alignment.Center),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                /*
                                * 알람 표시 시작일 위젯
                                * */
                                AlarmDisplayDatePicker(
                                    selectedNumber = selectedAlarmDisplayDate.amount ?: 1,
                                    selectedText = selectedAlarmDisplayDate.unit ?: "주",
                                    onDurationAmountChanged = { newAmount ->
                                        detailTodoViewModel.updateSelectedAlarmDisplayDate(
                                            newAmount,
                                            null
                                        )
                                    },
                                    onDurationUnitChanged = { newUnit ->
                                        detailTodoViewModel.updateSelectedAlarmDisplayDate(
                                            null,
                                            newUnit
                                        )
                                    }
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        detailTodoViewModel.clickedAlarmDisplayStartDateText()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF007AFF),
                                        contentColor = Color.White,
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .padding(horizontal = 24.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = stringResource(R.string.complete),
                                        textAlign = TextAlign.Center,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                }

                            }
                        }
                    }
                }

            }

            // 다이얼로그
            Column {
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
                        when (dialogMod) {
                            DialogMod.TODO_DELETE -> {
                                detailTodoViewModel.deleteTodoItem(todoItem?.id ?: 0)
                                navController?.popBackStack()
                            }

                            DialogMod.TODO_COMPLETE -> {
                                detailTodoViewModel.completeTodoItem(todoItem?.id ?: 0)
                                navController?.popBackStack()
                            }

                            DialogMod.TODO_CANCEL_COMPLETE -> {
                                detailTodoViewModel.completeCancelTodoItem(
                                    todoItem?.id ?: 0
                                )
                                navController?.popBackStack()
                            }

                            else -> {
                                showDialog = false
                            }
                        }
                        showDialog = false
                    },
                    title = todoItem?.title ?: stringResource(R.string.unknown),
                )
            }
        }
    }
}

@Composable
fun DatePicker(
    context: Context,
    detailTodoViewModel: DetailTodoViewModel,
    onDateSelected: (LocalDate) -> Unit,
    onMonthChanged: (Int) -> Unit,
) {
    val selectedDate by detailTodoViewModel.selectedDate.collectAsStateWithLifecycle()
    val currentDate = selectedDate ?: LocalDate.now()

    val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")

    // 달력 렌더링
    val renderCalendar: @Composable () -> Unit = {
        val year = currentDate.year
        val month = currentDate.monthValue
        val daysInMonth = currentDate.lengthOfMonth()
        val firstDayOfMonth = LocalDate.of(year, month, 1).dayOfWeek.value % 7
        val today = LocalDate.now()

        Column {
            // 주간 헤더
            Row {
                daysOfWeek.forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        color = colorResource(id = R.color.ios_gray_calander_font)
                    )
                }
            }

            for (week in 0..5) {
                Row {
                    for (day in 0..6) {
                        val dayOfMonth = week * 7 + day - firstDayOfMonth + 1
                        if (dayOfMonth in 1..daysInMonth) {
                            val isSelected = selectedDate?.dayOfMonth == dayOfMonth
                            val isToday = LocalDate.of(year, month, dayOfMonth).isEqual(today)

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(2.dp)
                                    .background(
                                        when {
                                            isSelected -> colorResource(id = R.color.ios_light_blue)
                                            else -> Color.Transparent
                                        },
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                    .clickable {
                                        onDateSelected(LocalDate.of(year, month, dayOfMonth))
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dayOfMonth.toString(),
                                    color = when {
                                        isSelected -> colorResource(id = R.color.ios_blue)
                                        isToday -> colorResource(id = R.color.ios_blue)
                                        else -> Color.Black
                                    },
                                    fontSize = when {
                                        isSelected -> 20.sp
                                        else -> 18.sp
                                    }
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentWidth()
            .widthIn(max = 500.dp, min = 400.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(ContextCompat.getColor(context, R.color.gray_menu))
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 날짜 표시 텍스트
                Text(
                    text = detailTodoViewModel.formatSelectedDateForCalendar(),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )

                // 월 변경 버튼
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    IconButton(onClick = { onMonthChanged(currentDate.monthValue - 1) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_left),
                            contentDescription = stringResource(R.string.cd_previous_month),
                            modifier = Modifier.size(20.dp),
                            tint = colorResource(id = R.color.ios_blue),
                        )
                    }
                    IconButton(onClick = { onMonthChanged(currentDate.monthValue + 1) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_right),
                            contentDescription = stringResource(R.string.cd_next_month),
                            modifier = Modifier.size(20.dp),
                            tint = colorResource(id = R.color.ios_blue)
                        )
                    }
                }
            }

            // 달력 렌더링
            renderCalendar()
        }
    }


}
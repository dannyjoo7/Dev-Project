package com.joo.miruni.presentation.detail.detailSchedule

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.joo.miruni.R
import com.joo.miruni.presentation.widget.BasicDialog
import com.joo.miruni.presentation.widget.DateRangePicker
import com.joo.miruni.presentation.widget.DialogMod


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScheduleScreen(
    detailScheduleViewModel: DetailScheduleViewModel = hiltViewModel(),
    scheduleId: Long = -1L,
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
    val scheduleItem by detailScheduleViewModel.scheduleItem.collectAsStateWithLifecycle()

    val isModified by detailScheduleViewModel.isModified.collectAsStateWithLifecycle()

    val titleText by detailScheduleViewModel.titleText.collectAsStateWithLifecycle()
    val descriptionText by detailScheduleViewModel.descriptionText.collectAsStateWithLifecycle()

    val showDateRangePicker by detailScheduleViewModel.showDateRangePicker.collectAsStateWithLifecycle()

    val selectedStartDate by detailScheduleViewModel.selectedStartDate.collectAsStateWithLifecycle()
    val selectedEndDate by detailScheduleViewModel.selectedEndDate.collectAsStateWithLifecycle()

    val isTitleTextEmpty by detailScheduleViewModel.isTitleTextEmpty.collectAsStateWithLifecycle()
    val isDateEmpty by detailScheduleViewModel.isDateEmpty.collectAsStateWithLifecycle()
    val isScheduleUpdate by detailScheduleViewModel.isScheduleAdded.collectAsStateWithLifecycle()

    /*
    * UI
    * */

    val titleTextColor = if (isTitleTextEmpty) Color.Red else colorResource(R.color.ios_gray)
    val dateTextColor =
        if (isDateEmpty) Color.Red else colorResource(R.color.ios_blue)

    var showDialog by remember { mutableStateOf(false) }                  // dialog 보여짐 여부
    var dialogMod by remember { mutableStateOf(DialogMod.SCHEDULE_DELETE) }              // dialog mod

    /*
    * 애니매이션
    * */

    val titleShakeOffset = remember { Animatable(0f) }
    val dateShakeOffset = remember { Animatable(0f) }

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

    LaunchedEffect(scheduleId) {
        if (scheduleId != -1L) {
            detailScheduleViewModel.loadScheduleDetails(scheduleId)
        }
    }

    // 항목 비었을 시 애니메이션 실행
    LaunchedEffect(isTitleTextEmpty, isDateEmpty) {
        if (isTitleTextEmpty) {
            titleShakeOffset.animateTo(
                targetValue = 0f,
                animationSpec = shakeKeyframes
            )
            detailScheduleViewModel.finishAnimation()
        }

        if (isDateEmpty) {
            dateShakeOffset.animateTo(
                targetValue = 0f,
                animationSpec = shakeKeyframes
            )
            detailScheduleViewModel.finishAnimation()
        }
    }


    // Schedule 추가 성공 시 해당 액티비티 종료
    LaunchedEffect(isScheduleUpdate) {
        if (isScheduleUpdate) {
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
                            text = stringResource(R.string.schedule),
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
                                    detailScheduleViewModel.updateScheduleItem()
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
        containerColor = Color.White,
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
                                dialogMod = DialogMod.SCHEDULE_DELETE
                                showDialog = true
                            },
                        painter = painterResource(id = R.drawable.ic_trash_can),
                        contentDescription = stringResource(R.string.cd_delete_schedule),
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
                                if (scheduleItem == null) {
                                    showDialog = false
                                } else {
                                    dialogMod = if (scheduleItem?.isComplete == true) {
                                        DialogMod.SCHEDULE_CANCEL_COMPLETE
                                    } else {
                                        DialogMod.SCHEDULE_COMPLETE
                                    }
                                    showDialog = true
                                }
                            },
                        painter = painterResource(
                            id = if (scheduleItem?.isComplete == true) {
                                R.drawable.ic_calendar_uncheck
                            } else {
                                R.drawable.ic_calendar_check
                            }
                        ),
                        contentDescription = stringResource(R.string.cd_complete_schedule),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.ios_blue)),
                    )
                }
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .padding(16.dp)
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // 일정 입력창
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 60.dp)
                            .offset {
                                IntOffset(
                                    x = titleShakeOffset.value
                                        .toDp()
                                        .toPx()
                                        .toInt(),
                                    y = 0
                                )
                            }
                    ) {
                        Text(
                            text = stringResource(R.string.schedule),
                            modifier = Modifier
                                .padding(end = 34.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        TextField(
                            value = titleText,
                            onValueChange = {
                                detailScheduleViewModel.updateTitleText(it)
                            },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.title_placeholder),
                                    fontSize = 16.sp,
                                    color = titleTextColor
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
                                detailScheduleViewModel.updateDescriptionText(it)
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

                // 시작일 및 종료일 설정창
                Column(
                    modifier = Modifier
                        .animateContentSize(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 60.dp)
                            .offset {
                                IntOffset(
                                    x = dateShakeOffset.value
                                        .toDp()
                                        .toPx()
                                        .toInt(),
                                    y = 0
                                )
                            },
                    ) {
                        Text(
                            text = stringResource(R.string.period),
                            modifier = Modifier
                                .weight(0.1f),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )


                        // 시작일 및 종료일
                        Column(
                            modifier = Modifier
                                .weight(0.9f)
                                .clickable {
                                    detailScheduleViewModel.clickedDateRangePickerBtn()
                                    keyboardController?.hide()
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = 4.dp,
                                    vertical = 4.dp,
                                ),
                                text = detailScheduleViewModel.formatSelectedDateForCalendar(
                                    selectedStartDate
                                ) ?: stringResource(R.string.select_date),
                                color = dateTextColor,
                                fontSize = 18.sp,
                            )
                            Image(
                                modifier = Modifier
                                    .size(8.dp),
                                painter = painterResource(id = R.drawable.ic_vertical_dot),
                                contentDescription = stringResource(R.string.cd_date_separator),
                                colorFilter = ColorFilter.tint(dateTextColor),
                            )
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = 4.dp,
                                    vertical = 4.dp,
                                ),
                                text = detailScheduleViewModel.formatSelectedDateForCalendar(
                                    selectedEndDate
                                ) ?: stringResource(R.string.select_date),
                                color = dateTextColor,
                                fontSize = 18.sp,
                            )
                        }


                    }
                    HorizontalDivider(color = Color.Gray, thickness = 0.5.dp)

                    /*
                    * 날짜 선택기
                    * */
                    Box {
                        // 날짜 선택기
                        androidx.compose.animation.AnimatedVisibility(
                            visible = showDateRangePicker,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                        ) {
                            DateRangePicker(
                                context = context,
                                selectedStartDate = selectedStartDate,
                                selectedEndDate = selectedEndDate,
                                onDateSelected = { startDate, endDate ->
                                    if (startDate == null && endDate == null) {
                                        detailScheduleViewModel.initSelectedDate()
                                    }
                                    if (startDate != null) {
                                        detailScheduleViewModel.selectStartDate(startDate)
                                    }
                                    if (endDate != null) {
                                        detailScheduleViewModel.selectEndDate(endDate)
                                    }
                                },
                                onDismiss = {
                                    detailScheduleViewModel.clickedDateRangePickerBtn()
                                }
                            )
                        }
                    }
                }
            }

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
                            DialogMod.SCHEDULE_DELETE -> {
                                detailScheduleViewModel.deleteScheduleItem(scheduleItem?.id ?: 0)
                                navController?.popBackStack()
                            }

                            DialogMod.SCHEDULE_COMPLETE -> {
                                detailScheduleViewModel.completeScheduleItem(scheduleItem?.id ?: 0)
                                navController?.popBackStack()
                            }

                            DialogMod.SCHEDULE_CANCEL_COMPLETE -> {
                                detailScheduleViewModel.completeCancelScheduleItem(
                                    scheduleItem?.id ?: 0
                                )
                                navController?.popBackStack()
                            }

                            else -> {
                                showDialog = false
                            }
                        }
                        showDialog = false
                    },
                    title = scheduleItem?.title ?: stringResource(R.string.unknown),
                )
            }
        }
    }
}
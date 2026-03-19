package com.joo.miruni.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.joo.miruni.R


enum class DialogMod {
    TODO_DELETE,                                // 할 일 삭제
    TODO_COMPLETE,                              // 할 일 완료
    TODO_CANCEL_COMPLETE,                       // 할 일 완료 취소
    TODO_ALL_DELAY,                             // 할 일 모두 미루기
    SCHEDULE_DELETE,                            // 일정 삭제
    SCHEDULE_COMPLETE,                          // 일정 완료
    SCHEDULE_CANCEL_COMPLETE,                   // 일정 완료 취소

    POST_NOTIFICATION_PERMISSION,               // 알림 권한 요청
    FOREGROUND_SERVICE_PERMISSION,                     // 포그라운드 서비스 권한 요청
    BATTERY_SETTING_PERMISSION,                 // 배터리 최적화 요청
}

// 대화상자
@Composable
fun BasicDialog(
    dialogType: DialogMod,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    onConfirmed: () -> Unit,
    title: String,
) {
    val dialogTitle: String
    val dialogContent: String
    val cancelButtonText: String
    val confirmButtonText: String

    when (dialogType) {
        DialogMod.TODO_DELETE -> {
            dialogTitle = stringResource(R.string.dialog_todo_delete_title)
            dialogContent = stringResource(R.string.dialog_todo_delete_content, title)
            cancelButtonText = stringResource(R.string.cancel)
            confirmButtonText = stringResource(R.string.delete)
        }

        DialogMod.TODO_COMPLETE -> {
            dialogTitle = stringResource(R.string.dialog_todo_complete_title)
            dialogContent = stringResource(R.string.dialog_todo_complete_content, title)
            cancelButtonText = stringResource(R.string.cancel)
            confirmButtonText = stringResource(R.string.complete)
        }

        DialogMod.TODO_CANCEL_COMPLETE -> {
            dialogTitle = stringResource(R.string.dialog_cancel_complete_title)
            dialogContent = stringResource(R.string.dialog_cancel_complete_content, title)
            cancelButtonText = stringResource(R.string.cancel)
            confirmButtonText = stringResource(R.string.dialog_cancel_complete_button)
        }

        DialogMod.TODO_ALL_DELAY -> {
            dialogTitle = stringResource(R.string.dialog_delay_all_title)
            dialogContent = stringResource(R.string.dialog_delay_all_content)
            cancelButtonText = stringResource(R.string.cancel)
            confirmButtonText = stringResource(R.string.confirm)
        }

        DialogMod.SCHEDULE_DELETE -> {
            dialogTitle = stringResource(R.string.dialog_schedule_delete_title)
            dialogContent = stringResource(R.string.dialog_schedule_delete_content, title)
            cancelButtonText = stringResource(R.string.cancel)
            confirmButtonText = stringResource(R.string.delete)
        }

        DialogMod.SCHEDULE_COMPLETE -> {
            dialogTitle = stringResource(R.string.dialog_schedule_complete_title)
            dialogContent = stringResource(R.string.dialog_schedule_complete_content, title)
            cancelButtonText = stringResource(R.string.cancel)
            confirmButtonText = stringResource(R.string.complete)
        }

        DialogMod.SCHEDULE_CANCEL_COMPLETE -> {
            dialogTitle = stringResource(R.string.dialog_cancel_complete_title)
            dialogContent = stringResource(R.string.dialog_cancel_complete_content, title)
            cancelButtonText = stringResource(R.string.cancel)
            confirmButtonText = stringResource(R.string.dialog_cancel_complete_button)
        }

        DialogMod.POST_NOTIFICATION_PERMISSION -> {
            dialogTitle = stringResource(R.string.dialog_notification_permission_title)
            dialogContent = stringResource(R.string.dialog_notification_permission_content)
            cancelButtonText = stringResource(R.string.cancel)
            confirmButtonText = stringResource(R.string.confirm)
        }

        DialogMod.BATTERY_SETTING_PERMISSION -> {
            dialogTitle = stringResource(R.string.dialog_battery_permission_title)
            dialogContent = stringResource(R.string.dialog_battery_permission_content)
            cancelButtonText = stringResource(R.string.cancel)
            confirmButtonText = stringResource(R.string.confirm)
        }

        DialogMod.FOREGROUND_SERVICE_PERMISSION -> {
            dialogTitle = stringResource(R.string.dialog_alarm_permission_title)
            dialogContent = stringResource(R.string.dialog_alarm_permission_content)
            cancelButtonText = stringResource(R.string.cancel)
            confirmButtonText = stringResource(R.string.confirm)
        }

    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .defaultMinSize(minWidth = 240.dp)
                    .width(260.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(colorResource(R.color.ios_dialog_gray)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 제목
                    Row(
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(
                            text = dialogTitle,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                        )
                    }

                    // 내용
                    Row(
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = dialogContent,
                            textAlign = TextAlign.Center,
                        )
                    }

                    // 버튼
                    HorizontalDivider(
                        thickness = 1.dp, color = Color.Gray.copy(alpha = 0.5f)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        // 왼쪽 버튼
                        Column(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .weight(1f)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    onConfirmed()
                                }
                                .padding(8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = confirmButtonText,
                                color = when (dialogType) {
                                    DialogMod.TODO_DELETE, DialogMod.SCHEDULE_DELETE -> {
                                        colorResource(R.color.ios_red)
                                    }

                                    else -> colorResource(R.color.ios_blue)
                                },
                                textAlign = TextAlign.Center,
                            )

                        }

                        // 구분선
                        Spacer(modifier = Modifier.width(4.dp))
                        VerticalDivider(
                            thickness = 1.dp,
                            color = Color.Gray.copy(alpha = 0.5f),
                            modifier = Modifier.height(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        // 오른쪽 버튼
                        Column(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .weight(1f)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    onCancel()
                                }
                                .padding(8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = cancelButtonText,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BasicDialogPreview() {
    // 미리보기용 다이얼로그
    BasicDialog(
        dialogType = DialogMod.FOREGROUND_SERVICE_PERMISSION,
        showDialog = true,
        onDismiss = { },
        onCancel = { },
        onConfirmed = { },
        title = "일정 제목"
    )
}

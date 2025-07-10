package com.example.aichat.ui.screens.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aichat.R
import com.example.aichat.data.models.Message
import com.example.aichat.ui.widgets.MessageCard

/**
 * チャット画面のメインコンポーザブル関数です。
 *
 * この関数は、入力テキストとメッセージ履歴の現在の状態を管理します。
 * 内部的には、UI要素の表示とインタラクションを処理する、
 * プライベートな `ChatScreen` コンポーザブル関数を呼び出します。
 *
 * @param modifier このコンポーザブルに適用する修飾子。デフォルトは Modifier です。
 */
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
) {
    var text by remember { mutableStateOf("") }
    //TODO: 今後修正予定
    val messages: List<Message> = emptyList()
    var isCommunicating by remember { mutableStateOf(false) }

    ChatScreen(
        modifier = modifier,
        text = text,
        onTextChange = { text = it },
        messages = messages,
        isCommunicating = isCommunicating,
        onDelete = { },
        onSettings = { },
        onSend = { text = "" },
        onCamera = { isCommunicating = !isCommunicating },
    )
}

/**
 * チャット画面のUIを構成するプライベートなコンポーザブル関数です。
 *
 * この関数は、Scaffoldを使用して、トップバーとメインコンテンツを含む画面レイアウトを定義します。
 * トップバーには、タイトル、削除ボタン、設定ボタンが表示されます。
 * メインコンテンツは、`MainContent` コンポーザブル関数によって表示されます。
 *
 * @param modifier このコンポーザブルに適用する修飾子。
 * @param text 入力フィールドに表示される現在のテキスト。
 * @param onTextChange 入力フィールドのテキストが変更されたときに呼び出されるコールバック関数。
 * @param messages 表示するメッセージのリスト。
 * @param onDelete 削除ボタンがクリックされたときに呼び出されるコールバック関数。
 * @param onSettings 設定ボタンがクリックされたときに呼び出されるコールバック関数。
 * @param onSend 送信ボタンがクリックされたときに呼び出されるコールバック関数。
 * @param onCamera カメラボタンがクリックされたときに呼び出されるコールバック関数。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreen(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    messages: List<Message>,
    isCommunicating: Boolean,
    onDelete: () -> Unit,
    onSettings: () -> Unit,
    onSend: () -> Unit,
    onCamera: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = { Text(text = "チャット画面") },
                actions = {
                    IconButton(
                        onClick = onDelete,
                        enabled = messages.isNotEmpty(),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "削除",
                        )
                    }

                    IconButton(onClick = onSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "設定",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        MainContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp),
            text = text,
            onTextChange = onTextChange,
            messages = messages,
            isCommunicating = isCommunicating,
            onSend = onSend,
            onCamera = onCamera,
        )
    }
}

/**
 * チャット画面のメインコンテンツを表示するコンポーザブル関数です。
 *
 * この関数は、メッセージ履歴をリスト表示し、メッセージ入力フィールドと送信ボタン、カメラボタンを提供します。
 * メッセージ履歴は `LazyColumn` を使用して効率的に表示されます。
 * 入力フィールドは `OutlinedTextField` を使用し、
 * 入力内容に応じて送信ボタンの有効/無効が切り替わります。
 *
 * @param modifier このコンポーザブルに適用する修飾子。デフォルトは Modifier です。
 * @param text 入力フィールドに表示される現在のテキスト。
 * @param onTextChange 入力フィールドのテキストが変更されたときに呼び出されるコールバック関数。
 * @param messages 表示するメッセージのリスト。
 * @param isCommunicating 通信中であるかどうかを示すブール値。
 * 通信中は送信ボタンの代わりにローディングインジケーターが表示されます。
 * @param onSend 送信ボタンがクリックされたときに呼び出されるコールバック関数。
 * @param onCamera カメラボタンがクリックされたときに呼び出されるコールバック関数。
 */
@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    messages: List<Message>,
    isCommunicating: Boolean,
    onSend: () -> Unit,
    onCamera: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            reverseLayout = true,
        ) {
            items(
                items = messages,
                key = { message -> message.id },
            ) { message ->
                MessageCard(
                    modifier = Modifier.padding(bottom = 8.dp),
                    message = message,
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onCamera) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.baseline_camera_alt_24
                    ),
                    contentDescription = "カメラ",
                )
            }

            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = text,
                onValueChange = onTextChange,
                minLines = 1,
                maxLines = 3,
                placeholder = { Text(text = "メッセージを入力してください") },
            )

            if(isCommunicating) {
                CircularProgressIndicator()
            } else {
                IconButton(
                    onClick = onSend,
                    enabled = text.isNotEmpty(),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "送信",
                    )
                }
            }
        }
    }
}

/**
 * ChatScreenコンポーザブルのプレビュー関数です。
 *
 * この関数は、Android Studioのプレビュー機能でChatScreenの表示を確認するために使用されます。
 * サンプルのメッセージ履歴と固定のテキスト、空のコールバック関数でChatScreenを呼び出します。
 */
@Preview
@Composable
private fun ChatScreenPreview() {
    val sampleHistory = listOf(
        Message(id = 1, text = "こんにちは！", author = Message.Author.USER),
        Message(id = 2, text = "これはAIからの返信です。", author = Message.Author.AI),
        Message(id = 3, text = "もう一度ユーザーからのメッセージです。", author = Message.Author.USER)
    )
    ChatScreen(
        text = "サンプルテキスト",
        onTextChange = {},
        messages = sampleHistory,
        isCommunicating = false,
        onDelete = {},
        onSettings = {},
        onSend = {},
        onCamera = {}
    )
}

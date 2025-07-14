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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.aichat.ui.screens.chat.widgets.MessageCard

/**
 * チャット画面のメインコンポーザブル関数です。
 *
 * この関数は、入力テキストとメッセージ履歴の現在の状態を管理します。
 * 内部的には、UI要素の表示とインタラクションを処理する、
 * プライベートな `ChatScreen` コンポーザブル関数を呼び出します。
 *
 * @param modifier このコンポーザブルに適用する修飾子。デフォルトは Modifier です。
 * @param navigateToSettings 設定画面へ遷移するためのコールバック関数。
 */
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    navigateToSettings: () -> Unit = {},
) {
    //TODO: 削除予定
    var uiState by remember {
        mutableStateOf(
            ChatUiState()
        )
    }

    ChatScreen(
        modifier = modifier,
        uiState = uiState,
        onTextChange = {
            //TODO: 修正予定
            uiState = uiState.copy(
                text = it
            )
        },
        onDelete = {
            //TODO: 修正予定
            uiState = uiState.copy(
                messages = emptyList()
            )
        },
        onSettings = navigateToSettings,
        onSend = {
            //TODO: 修正予定
            val messageSize = uiState.messages.size
            val author = if (messageSize % 2 == 0) Message.Author.USER else Message.Author.AI

            val message = Message(
                id = messageSize.toLong(),
                text = uiState.text,
                author = author,
            )
            uiState = uiState.copy(
                messages = uiState.messages + message,
                text = "",
            )
        },
        onCamera = {
            //TODO: 修正予定
            uiState = uiState.copy(
                userMessage = "カメラ機能は未実装です"
            )
        },
        userMessageShown = {
            //TODO: 修正予定
            uiState = uiState.copy(
                userMessage = null
            )
        },
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
 * @param uiState チャット画面の状態を保持するオブジェクト。
 * @param onTextChange 入力フィールドのテキストが変更されたときに呼び出されるコールバック関数。
 * @param onDelete 削除ボタンがクリックされたときに呼び出されるコールバック関数。
 * @param onSettings 設定ボタンがクリックされたときに呼び出されるコールバック関数。
 * @param onSend 送信ボタンがクリックされたときに呼び出されるコールバック関数。
 * @param onCamera カメラボタンがクリックされたときに呼び出されるコールバック関数。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreen(
    modifier: Modifier = Modifier,
    uiState: ChatUiState,
    userMessageShown: () -> Unit,
    onTextChange: (String) -> Unit,
    onDelete: () -> Unit,
    onSettings: () -> Unit,
    onSend: () -> Unit,
    onCamera: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val userMessage = uiState.userMessage
    LaunchedEffect(userMessage) {
        if(userMessage == null) return@LaunchedEffect

        snackBarHostState.showSnackbar(
            message = userMessage,
        )
        userMessageShown()
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackBarHostState) },
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
                        enabled = uiState.messages.isNotEmpty(),
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
            text = uiState.text,
            onTextChange = onTextChange,
            messages = uiState.messages.reversed(),
            isCommunicating = uiState.isCommunicating,
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
 * `ChatScreen` コンポーザブルのプレビュー関数です。
 *
 * この関数は、Android Studioのプレビュー機能でChatScreenの表示を確認するために使用されます。
 * サンプルのメッセージ履歴と固定のテキスト、空のコールバック関数でChatScreenを呼び出します。
 */
@Preview
@Composable
private fun ChatScreenPreview() { // ktlint-disablefilename
    val sampleHistory = listOf(
        Message(id = 1, text = "こんにちは！", author = Message.Author.USER),
        Message(id = 2, text = "これはAIからの返信です。", author = Message.Author.AI),
        Message(id = 3, text = "もう一度ユーザーからのメッセージです。", author = Message.Author.USER)
    )
    ChatScreen(
        uiState = ChatUiState(
            text = "サンプルテキスト",
            messages = sampleHistory,
            isCommunicating = false,
        ),
        onTextChange = {},
        onDelete = {},
        onSettings = {},
        onSend = {},
        onCamera = {},
        userMessageShown = {}
    )
}
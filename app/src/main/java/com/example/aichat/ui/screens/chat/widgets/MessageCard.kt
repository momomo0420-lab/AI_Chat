package com.example.aichat.ui.screens.chat.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.aichat.data.models.Message
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aichat.util.DateFormatter

/**
 * メッセージの作成者に基づいて、ユーザーメッセージカードまたはAIメッセージカードを表示します。
 *
 * @param modifier このコンポーザブルに適用する修飾子。
 * @param message 表示するメッセージデータを含む [Message] オブジェクト。
 */
@Composable
fun MessageCard(
    modifier: Modifier = Modifier,
    message: Message,
) {
    when(message.author) {
        Message.Author.USER -> UserMessageCard(
            modifier = modifier,
            message = message
        )
        Message.Author.AI -> AiMessageCard(
            modifier = modifier,
            message = message
        )
    }
}

/**
 * メッセージカードのプレビューを表示します。
 *
 * このコンポーザブル関数は、AIメッセージとユーザーメッセージの両方を含む列をレンダリングし、
 * それぞれにパディングが適用され、背景色が設定された列内に配置されます。
 */
@Preview
@Composable
private fun MessageCardPreview() {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    ) {
        MessageCard(
            modifier = Modifier.padding(8.dp),
            message = Message(
                id = 1,
                text = "This is a sample AI message.",
                author = Message.Author.AI
            )
        )

        MessageCard(
            modifier = Modifier.padding(8.dp),
            message = Message(
                id = 1,
                text = "This is a sample message.",
                author = Message.Author.USER
            )
        )
    }
}

/**
 * AIメッセージのメッセージカードを表示します。
 *
 * このコンポーザブル関数は、AIのアイコン、メッセージテキスト、
 * およびメッセージのタイムスタンプを含む行をレイアウトします。
 * メッセージカードは行の先頭に配置されます。
 *
 * @param modifier カードのルート行に適用される修飾子。
 * @param message 表示するデータを含む [Message] オブジェクト。
 */
@Composable
private fun AiMessageCard(
    modifier: Modifier = Modifier,
    message: Message
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
    ) {
        Box(
            modifier = Modifier.size(40.dp).background(
                color = MaterialTheme.colorScheme.secondary,
                shape = CircleShape,
            ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.onSecondary,
                text = "AI",
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = message.text,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.align(Alignment.Bottom),
            horizontalAlignment = Alignment.End,
        ) {
            val date = DateFormatter.toDateOnly(message.createdAt)
            Text(date)
            val time = DateFormatter.toTimeOnly(message.createdAt)
            Text(time)
        }
    }
}

/**
 * AIメッセージのメッセージカードのプレビュー。
 *
 * このコンポーザブル関数は、AIメッセージカードのプレビューを表示します。
 * 背景が設定された列内に、サンプルのAIメッセージを表示します。
 */
@Preview
@Composable
private fun AiMessageCardPreview() {
    val message = Message(
        id = 1,
        text = "This is a sample AI message.\naaaaaaa",
        author = Message.Author.AI
    )
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    ) {
        AiMessageCard(modifier = Modifier, message = message)
    }
}

/**
 * ユーザーメッセージのメッセージカードを表示します。
 *
 * このコンポーズ可能な関数は、メッセージの内容（メッセージテキスト、作成日、作成時刻など）を
 * 行に配置します。メッセージカードは行の末尾に配置されます。
 *
 * @param modifier カードのルート行に適用される修飾子。
 * @param message 表示するデータを含む [Message] オブジェクト。
 */
@Composable
private fun UserMessageCard(
    modifier: Modifier = Modifier,
    message: Message
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End,
    ) {
        Column(
            modifier = Modifier.align(Alignment.Bottom),
            horizontalAlignment = Alignment.End,
        ) {
            val date = DateFormatter.toDateOnly(message.createdAt)
            Text(date)
            val time = DateFormatter.toTimeOnly(message.createdAt)
            Text(time)
        }
        Spacer(modifier = Modifier.width(8.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            ),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = message.text,
            )
        }
    }
}

/**
 * [UserMessageCard] コンポーザブルのプレビュー。
 * このプレビューでは、背景色の付いた列内にサンプルのユーザーメッセージが表示されます。
 */
@Preview
@Composable
private fun UserMessageCardPreview() {
    val message = Message(
        id = 1,
        text = "This is a sample user message.",
        author = Message.Author.USER
    )
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    ) {
        UserMessageCard(
            modifier = Modifier,
            message = message
        )
    }
}

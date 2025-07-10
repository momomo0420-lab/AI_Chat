package com.example.aichat.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * 設定画面のコンポーザブル関数。
 *
 * このコンポーザブル関数は設定画面を表示し、ユーザーがアプリケーションを設定できるようにします。
 * 現在、API キーの設定のみが可能です。
 *
 * @param modifier コンポーザブルに適用する修飾子。
 * @param onBack 戻るボタンが押されたときに呼び出されるコールバック関数。
 */
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    var apiKey by remember { mutableStateOf("") }

    SettingsScreen(
        modifier = modifier,
        apiKey = apiKey,
        onApiKeyChange = { apiKey = it },
        onBack = onBack,
        onSave = {
            /* TODO: APIキーを保存する */
            onBack()
        },
    )
}

/**
 * 設定画面の内部実装。
 *
 * このコンポーザブル関数は、設定画面のUI要素（トップバーとメインコンテンツ）を構成します。
 *
 * @param modifier このコンポーザブルに適用する修飾子。
 * @param apiKey 現在のAPIキーの値。
 * @param onApiKeyChange APIキーが変更されたときに呼び出されるコールバック関数。新しいAPIキーを引数に取ります。
 * @param onBack 戻るボタンが押されたときに呼び出されるコールバック関数。
 * @param onSave 保存ボタンが押されたときに呼び出されるコールバック関数。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreen(
    modifier: Modifier = Modifier,
    apiKey: String,
    onApiKeyChange: (String) -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(text = "設定画面") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "戻る",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSave) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "完了",
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        MainContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp),
            apiKey = apiKey,
            onApiKeyChange = onApiKeyChange,
        )
    }
}

/**
 * 設定画面のメインコンテンツを表示するコンポーザブル関数。
 *
 * この関数は、APIキーを入力するためのテキストフィールドを表示します。
 *
 * @param modifier このコンポーザブルに適用する修飾子。
 * @param apiKey 現在のAPIキーの値。
 * @param onApiKeyChange APIキーが変更されたときに呼び出されるコールバック関数。新しいAPIキーを引数に取ります。
 */
@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    apiKey: String,
    onApiKeyChange: (String) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Text(text = "APIキー：")
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text(text = "APIキーを入力してください") },
            value = apiKey,
            onValueChange = onApiKeyChange,
            trailingIcon = {
                IconButton(onClick = { onApiKeyChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "クリア",
                    )
                }
            }
        )
    }
}

/**
 * SettingsScreenのプレビュー用コンポーザブル関数。
 *
 * この関数は、開発中にSettingsScreenの見た目や動作を確認するために使用されます。
 */
@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen()
}

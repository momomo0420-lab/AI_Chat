package com.example.aichat.data.models

/**
 * チャット内のメッセージを表します。
 *
 * @property id メッセージの一意な ID。
 * @property text メッセージのテキストコンテンツ。
 * @property author メッセージの作成者（USER または AI）。
 * @property createdAt メッセージの作成タイムスタンプ（ミリ秒単位）。
 */
data class Message(
    val id: Long = 0,
    val text: String,
    val author: Author,
    val createdAt: Long = System.currentTimeMillis(),
) {
    /**
     * メッセージの作成者を表します。
     */
    enum class Author {
        USER,
        AI,
    }
}

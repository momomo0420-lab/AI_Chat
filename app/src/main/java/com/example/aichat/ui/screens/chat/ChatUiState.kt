package com.example.aichat.ui.screens.chat

import com.example.aichat.data.models.Message

data class ChatUiState(
    val userMessage: String? = null,
    val text: String = "",
    val messages: List<Message> = emptyList(),
    val isCommunicating: Boolean = false,
)

package com.example.aichat.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 日付と時刻をフォーマットするためのユーティリティオブジェクト。
 *
 * このオブジェクトは、タイムスタンプ（ミリ秒単位）を、
 * 特に日本語ロケール向けにフォーマットされた、人間が読める日付と時刻の文字列に変換する関数を提供します。
 */
object DateFormatter {
    private val dateOnlyFormatter = SimpleDateFormat("M月d日", Locale.JAPAN)
    private val timeOnlyFormatter = SimpleDateFormat("HH:mm", Locale.JAPAN)

    /**
     * タイムスタンプを日付のみの文字列に変換します。
     *
     * 出力形式は「M月d日」です（例：1月1日の場合は「1月1日」）。
     *
     * @param timeStamp Unixエポックからのミリ秒単位のタイムスタンプ。
     * @return タイムスタンプの日付部分を表す文字列。
     */
    fun toDateOnly(timeStamp: Long): String {
        val date = Date(timeStamp)
        return dateOnlyFormatter.format(date)
    }

    /**
     * タイムスタンプを時刻のみの文字列に変換します。
     *
     * 出力形式は "HH:mm" です（例：午後1時45分は "13:45"）。
     *
     * @param timeStamp Unixエポックからのミリ秒単位のタイムスタンプ。
     * @return タイムスタンプの時刻部分を表す文字列。
     */
    fun toTimeOnly(timeStamp: Long): String {
        val date = Date(timeStamp)
        return timeOnlyFormatter.format(date)
    }
}

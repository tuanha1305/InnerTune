package com.zionhuang.innertube.models

import android.util.Base64
import kotlinx.serialization.Serializable

@Serializable
data class YouTubeClient(
    val clientName: String,
    val clientVersion: String,
    val api_key: String,
    val userAgent: String,
    val referer: String? = null,
) {
    fun toContext(locale: YouTubeLocale, visitorData: String?) = Context(
        client = Context.Client(
            clientName = clientName,
            clientVersion = clientVersion,
            gl = locale.gl,
            hl = locale.hl,
            visitorData = visitorData
        )
    )

    companion object {
        fun generateVisitorData(): String {
            val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toList()
            var id = ""
            for (i in 1..11) {
                id += alphabet.random()
            }
            val timestamp = System.currentTimeMillis() / 1000
            return generateVisitorData(id, timestamp.toInt())
        }

        fun generateVisitorData(id: String, timestamp: Int): String {
            val visitorData = VisitorData.visitorData.newBuilder()
                .setId(id)
                .setTimestamp(timestamp)
                .build()
            return Base64.encodeToString(visitorData.toByteArray(), Base64.NO_WRAP)
                .replace("==", "%3D%3D")
                .replace("+", "-")
                .replace("/", "_")
        }

        private const val REFERER_YOUTUBE_MUSIC = "https://music.youtube.com/"

        private const val USER_AGENT_WEB = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36"
        private const val USER_AGENT_ANDROID = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Mobile Safari/537.36"

        val ANDROID_MUSIC = YouTubeClient(
            clientName = "ANDROID_MUSIC",
            clientVersion = "5.01",
            api_key = "AIzaSyAOghZGza2MQSZkY_zfZ370N-PUdXEo8AI",
            userAgent = USER_AGENT_ANDROID
        )

        val ANDROID = YouTubeClient(
            clientName = "ANDROID",
            clientVersion = "17.13.3",
            api_key = "AIzaSyA8eiZmM1FaDVjRy-df2KTyQ_vz_yYM39w",
            userAgent = USER_AGENT_ANDROID,
        )

        val WEB_REMIX = YouTubeClient(
            clientName = "WEB_REMIX",
            clientVersion = "1.20220606.03.00",
            api_key = "AIzaSyC9XL3ZjWddXya6X74dJoCTL-WEYFDNX30",
            userAgent = USER_AGENT_WEB,
            referer = REFERER_YOUTUBE_MUSIC
        )
    }
}

package com.sunstar.lsvideopic

import android.media.MediaMetadataRetriever
import android.util.Log

object VideoUtil {
    fun getFrameImg(path: String, second: Int): String {
        var result = ""
        var mmr = MediaMetadataRetriever()
        mmr.setDataSource(path)
        var duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        Log.d("VideoUtil",duration)
        return result
    }
}
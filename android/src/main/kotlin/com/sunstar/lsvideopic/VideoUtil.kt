package com.sunstar.lsvideopic

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.plugin.common.MethodChannel
import io.microshow.rxffmpeg.RxFFmpegCommandList
import io.microshow.rxffmpeg.RxFFmpegInvoke
import io.microshow.rxffmpeg.RxFFmpegInvoke.getInstance
import io.microshow.rxffmpeg.RxFFmpegSubscriber
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

object VideoUtil {
    @SuppressLint("CheckResult")
    fun getFrameImg(context: Context, path: String, second: Long, @NonNull myResult: MethodChannel.Result): String {
        RxFFmpegInvoke.getInstance().setDebug(true);
        var result = ""
        var mmr = MediaMetadataRetriever()
        mmr.setDataSource(path)
        var duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        Log.d("VideoUtil", duration)
        if (second > duration.toLong()) {
            result = ""
        }
        var resultPath = FileSDCardUtil().saveToSDcarPrivateFiles(context, "cacheFile") + "${System.currentTimeMillis()}.png"
        val text = "ffmpeg -y -i $path -f image2 -ss ${getTime(second)} -vframes 1 -preset superfast $resultPath"
        val commands = text.split(" ").toTypedArray()
        getInstance().runCommandRxJava(commands).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : RxFFmpegSubscriber() {
            override fun onFinish() {
                myResult.success(resultPath)
            }

            override fun onCancel() {

            }

            override fun onProgress(progress: Int, progressTime: Long) {
            }

            override fun onError(message: String?) {
            }
        })
        return result
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(time: Long): String {
        var ms = time
        var formatter = SimpleDateFormat("HH:mm:ss");
        formatter.timeZone = TimeZone.getTimeZone("GMT+00:00");
        var hms = formatter.format(ms);
        return hms;
    }

    private fun commendAdd(videoPath: String, time: Int): Array<String> {
        var commedString = RxFFmpegCommandList()
        commedString.append("-i")
        commedString.append(videoPath)
        commedString.append("-f")
        commedString.append("image2")
        commedString.append("-ss")
        commedString.append("00:00:03")
        commedString.append("-vframes")
        commedString.append("1")
        commedString.append("-preset")
        commedString.append("superfast")
        commedString.append("/storage/emulated/0/1/result.jpg")
        return commedString.build()
    }
}
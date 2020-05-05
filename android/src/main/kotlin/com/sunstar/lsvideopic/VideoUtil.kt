package com.sunstar.lsvideopic

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaMetadataRetriever
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import io.flutter.plugin.common.MethodChannel
import io.microshow.rxffmpeg.RxFFmpegCommandList
import io.microshow.rxffmpeg.RxFFmpegInvoke
import io.microshow.rxffmpeg.RxFFmpegInvoke.getInstance
import io.microshow.rxffmpeg.RxFFmpegSubscriber
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import top.zibin.luban.Luban
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

object VideoUtil {
    @SuppressLint("CheckResult")
    fun getFrameImg(context: Context, path: String, second: Long, @NonNull myResult: MethodChannel.Result){
        RxFFmpegInvoke.getInstance().setDebug(true);
        var result = ""
        var mmr = MediaMetadataRetriever()
        mmr.setDataSource(path)
        var duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        Log.d("VideoUtil", duration)
        if (second > duration.toLong()) {
            myResult.error("-1","secondError","时间超出视频时长")
            return
        }
        var resultPath = FileSDCardUtil().saveToSDcarPrivateFiles(context, "cache") + "${System.currentTimeMillis()}.png"
        val text = "ffmpeg -y -i $path -f image2 -ss ${getTime(second)} -vframes 1 -preset superfast $resultPath"
        val commands = text.split(" ").toTypedArray()
        getInstance().runCommandRxJava(commands).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : RxFFmpegSubscriber() {
            override fun onFinish() {
//                myResult.success(resultPath)
                getCacheFile(resultPath, context, myResult)
            }

            override fun onCancel() {

            }

            override fun onProgress(progress: Int, progressTime: Long) {
            }

            override fun onError(message: String?) {
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun getCacheFile(resultPath: String, context: Context, @NonNull myResult: MethodChannel.Result) {
        Observable.just(resultPath).map {
            var imgPath = FileSDCardUtil().saveToSDcarPrivateFiles(context, "cacheImageFile")
            Luban.with(context).ignoreBy(200).setTargetDir(imgPath).load(it).get()
        }.map {
            File(resultPath).delete()
            it
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            myResult.success(it[0].path)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(time: Long): String {
        var ms = time
        var formatter = SimpleDateFormat("HH:mm:ss");
        formatter.timeZone = TimeZone.getTimeZone("GMT+00:00");
        var hms = formatter.format(ms);
        return hms;
    }

}
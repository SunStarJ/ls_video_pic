package com.sunstar.lsvideopic

import android.content.Context
import android.os.Environment
import java.io.File

class FileSDCardUtil {
    /**
     * 作者：GaoXiaoXiong
     * 创建时间:2019/1/26
     * 注释描述:返回下载的APP目录
     */
    fun saveDownAppToSDcarPrivateFiles(context: Context): String? {
        var path: String? = null
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) { //此目录下的是外部存储下的files/downAppDirs目录
            val file = context.getExternalFilesDir(DownAppDirs) //SDCard/Android/data/你的应用的包名/files/downAppDirs
            if (!file.exists()) {
                file.mkdirs()
            }
            path = file.absolutePath
        } else {
            path = context.filesDir.absolutePath + "/" + DownAppDirs
            val file = File(path)
            if (!file.exists()) {
                file.mkdirs()
            }
            path = file.absolutePath
        }
        return path //SDCard/Android/data/你的应用的包名/files/downAppDirs
    }

    fun saveToSDcarPrivateFiles(context: Context, fileName: String): String {
        var path: String? = null
        //地址 /storage/emulated/0/Android/data/包名/files/fileName
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) { //此目录下的是外部存储下的files/fileName目录
            val file = context.getExternalFilesDir(fileName) //SDCard/Android/data/你的应用的包名/files/fileName
            if (!file.exists()) {
                file.mkdirs()
            }
            path = file.absolutePath
        } else {
            path = context.filesDir.absolutePath + "/" + fileName
            val file = File(path)
            if (!file.exists()) {
                file.mkdirs()
            }
            path = file.absolutePath
        }
        return "$path/"
    }

    /**
     * 作者：GaoXiaoXiong
     * 创建时间:2019/1/26
     * 注释描述:获取缓存目录
     * @fileName 获取外部存储目录下缓存的 fileName的文件夹路径
     */
    fun getDiskCacheDir(context: Context, fileName: String): String {
        var cachePath: String? = null
        cachePath = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) { //此目录下的是外部存储下的私有的fileName目录
            context.externalCacheDir.path + "/" + fileName //SDCard/Android/data/你的应用包名/cache/fileName
        } else {
            context.cacheDir.path + "/" + fileName
        }
        val file = File(cachePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath //SDCard/Android/data/你的应用包名/cache/fileName/
    }

    /**
     * @date: 2019/8/2 0002
     * @author: gaoxiaoxiong
     * @description:获取外部存储目录下的 fileName的文件夹路径
     */
    fun getDiskFileDir(context: Context, fileName: String): String {
        var cachePath: String? = null
        cachePath = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) { //此目录下的是外部存储下的私有的fileName目录
            context.getExternalFilesDir(fileName).absolutePath //mnt/sdcard/Android/data/com.my.app/files/fileName
        } else {
            context.filesDir.path + "/" + fileName //data/data/com.my.app/files
        }
        val file = File(cachePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath //mnt/sdcard/Android/data/com.my.app/files/fileName
    }

    /**
     * 作者：GaoXiaoXiong
     * 创建时间:2019/1/26
     * 注释描述:从sdcard中删除文件
     */
    fun removeFileFromSDCard(filePath: String?): Boolean {
        val file = File(filePath)
        return if (file.exists()) {
            try {
                file.delete()
                true
            } catch (e: Exception) {
                false
            }
        } else {
            false
        }
    }

    companion object {
        var fileSDCardUtil: FileSDCardUtil? = null
        var DownAppDirs = "downAppDirs" //APP下载目录
        var ImagePicCacheDir = "/gxximages" //图片缓存目录
        var MusicCacheDir = "/gxxmusic" //音乐的缓存目录
        val instance: FileSDCardUtil?
            get() {
                if (fileSDCardUtil == null) {
                    synchronized(FileSDCardUtil::class.java) {
                        if (fileSDCardUtil == null) {
                            fileSDCardUtil = FileSDCardUtil()
                        }
                    }
                }
                return fileSDCardUtil
            }
    }
}
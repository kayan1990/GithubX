package com.kayan.github.common.utils

import android.content.Context
import android.graphics.Point
import android.widget.ImageView
import androidx.core.net.toUri
import com.kayan.github.GithubApplication
import com.kayan.github.kotlin.R
import com.kayan.github.AppConfig
import com.kayan.github.common.imageloader.ImageLoaderManager
import com.kayan.github.common.imageloader.LoadOption
import com.kayan.github.common.style.image.BlurTransformation
import com.kayan.github.module.image.ImagePreViewActivity
import com.kayan.github.module.person.PersonActivity
import com.kayan.github.module.repos.ReposDetailActivity
import org.jetbrains.anko.browse
import java.text.SimpleDateFormat
import java.util.*


/**
 * 通用工具类
 */
object CommonUtils {

    private const val MILLIS_LIMIT = 1000.0

    private const val SECONDS_LIMIT = 60 * MILLIS_LIMIT

    private const val MINUTES_LIMIT = 60 * SECONDS_LIMIT

    private const val HOURS_LIMIT = 24 * MINUTES_LIMIT

    private const val DAYS_LIMIT = 30 * HOURS_LIMIT


    /**
     * 加载用户头像
     */
    fun loadUserHeaderImage(imageView: ImageView, url: String, size: Point = Point(50.dp, 50.dp)) {
        val option = LoadOption()
                .setDefaultImg(R.drawable.logo)
                .setErrorImg(R.drawable.logo)
                .setCircle(true)
                .setSize(size)
                .setUri(url)
        ImageLoaderManager.sInstance.imageLoader().loadImage(option, imageView, null)
    }

    /**
     * 加载高斯模糊图片
     */
    fun loadImageBlur(imageView: ImageView, url: String) {
        val process = BlurTransformation()
        val option = LoadOption()
                .setDefaultImg(R.drawable.logo)
                .setErrorImg(R.drawable.logo)
                .setUri(url)
                .setTransformations(process)

        ImageLoaderManager.sInstance.imageLoader().loadImage(option, imageView, null)
    }


    fun getDateStr(date: Date?): String {
        if (date?.toString() == null) {
            return ""
        } else if (date.toString().length < 10) {
            return date.toString()
        }
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date).substring(0, 10)
    }

    /**
     * 获取时间格式化
     */
    fun getNewsTimeStr(date: Date?): String {
        if (date == null) {
            return ""
        }
        val subTime = Date().time - date.time
        return when {
            subTime < MILLIS_LIMIT -> GithubApplication.instance.getString(R.string.justNow)
            subTime < SECONDS_LIMIT -> Math.round(subTime / MILLIS_LIMIT).toString() + " " + GithubApplication.instance.getString(R.string.secondAgo)
            subTime < MINUTES_LIMIT -> Math.round(subTime / SECONDS_LIMIT).toString() + " " + GithubApplication.instance.getString(R.string.minutesAgo)
            subTime < HOURS_LIMIT -> Math.round(subTime / MINUTES_LIMIT).toString() + " " + GithubApplication.instance.getString(R.string.hoursAgo)
            subTime < DAYS_LIMIT -> Math.round(subTime / HOURS_LIMIT).toString() + " " + GithubApplication.instance.getString(R.string.daysAgo)
            else -> getDateStr(date)
        }
    }


    fun getReposHtmlUrl(userName: String, reposName: String): String =
            AppConfig.GITHUB_BASE_URL + userName + "/" + reposName

    fun getIssueHtmlUrl(userName: String, reposName: String, number: String): String =
            AppConfig.GITHUB_BASE_URL + userName + "/" + reposName + "/issues/" + number

    fun getUserHtmlUrl(userName: String) =
            AppConfig.GITHUB_BASE_URL + userName

    fun getFileHtmlUrl(userName: String, reposName: String, path: String, branch: String = "master"): String =
            AppConfig.GITHUB_BASE_URL + userName + "/" + reposName + "/blob/" + branch + "/" + path

    fun getCommitHtmlUrl(userName: String, reposName: String, sha: String): String =
            AppConfig.GITHUB_BASE_URL + userName + "/" + reposName + "/commit/" + sha


    fun launchUrl(context: Context, url: String?) {
        if (url == null || url.isEmpty()) return
        val parseUrl = url.toUri()
        var isImage = isImageEnd(parseUrl.toString())
        if (parseUrl.toString().endsWith("?raw=true")) {
            isImage = isImageEnd(parseUrl.toString().replace("?raw=true", ""))
        }
        if (isImage) {
            var imageUrl = url
            if (!parseUrl.toString().endsWith("?raw=true")) {
                imageUrl = "$url?raw=true"
            }
            ImagePreViewActivity.gotoImagePreView(imageUrl)
            return
        }

        if (parseUrl.host == "github.com" && parseUrl.path!!.isNotEmpty()) {
            val pathNames = parseUrl.path!!.split("/")
            if (pathNames.size == 2) {
                //解析人
                val userName = pathNames[1]
                PersonActivity.gotoPersonInfo(userName)
            } else if (pathNames.size >= 3) {
                val userName = pathNames[1]
                val repoName = pathNames[2]
                //解析仓库
                if (pathNames.size == 3) {
                    ReposDetailActivity.gotoReposDetail(userName, repoName)
                } else {
                    context.browse(url)
                }
            }
        } else if (url.startsWith("http")) {
            context.browse(url)
        }
    }

    private val sImageEndTag = arrayListOf(".png", ".jpg", ".jpeg", ".gif", ".svg")

    fun isImageEnd(path: String): Boolean {
        var image = false
        sImageEndTag.forEach {
            if (path.indexOf(it) + it.length == path.length) {
                image = true
            }
        }
        return image
    }

}
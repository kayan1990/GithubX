package com.kayan.github

import android.app.Activity
import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.kayan.github.common.imageloader.ImageLoaderManager
import com.kayan.github.common.imageloader.giideloader.GlideImageLoader
import com.kayan.github.common.style.Iconfont
import com.kayan.github.di.AppInjector
import com.kayan.github.kotlin.BuildConfig
import com.mikepenz.iconics.Iconics
import com.tencent.bugly.crashreport.CrashReport
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import javax.inject.Inject
import kotlin.properties.Delegates


class GithubApplication : Application(), HasActivityInjector {

    companion object {
        var instance: GithubApplication by Delegates.notNull()
    }

    /**
     * 分发Activity的注入
     *
     * 在Activity调用AndroidInjection.inject(this)时
     * 从Application获取一个DispatchingAndroidInjector<Activity>，并将activity传递给inject(activity)
     * DispatchingAndroidInjector通过AndroidInjector.Factory创建AndroidInjector
     */
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        instance = this

        ///初始化路由
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)

        //Application级别注入
        AppInjector.init(this)

        ///初始化图标库
        Iconics.init(applicationContext)
        Iconics.registerFont(Iconfont)

        ///初始化图片加载
        ImageLoaderManager.initialize(GlideImageLoader(this))

        ///数据库
        Realm.init(this)
//        RealmFactory.instance


        if (!BuildConfig.DEBUG) {
            ///bugly
            CrashReport.initCrashReport(applicationContext, "209f33d74f", false)
        }

//        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
//
//            override fun placeholder(ctx: Context): Drawable {
//                return getDrawable(R.drawable.logo)!!
//            }
//
//            override fun placeholder(ctx: Context, tag: String?): Drawable {
//                return getDrawable(R.drawable.logo)!!
//            }
//            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable, tag: String?) {
//                CommonUtils.loadUserHeaderImage(imageView, uri.toString())
//            }
//        })
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
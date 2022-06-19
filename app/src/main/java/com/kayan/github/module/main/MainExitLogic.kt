package com.kayan.github.module.main

import com.kayan.github.kotlin.R
import org.jetbrains.anko.toast

/**
 * 主页退出逻辑
 * Created by kayan
 * Date: 2018-11-09
 */
class MainExitLogic(private val activity: MainActivity) {

    var firstTime = 0L


    fun backPress() {
        val secondTime = System.currentTimeMillis()
        if (secondTime - firstTime > 2000) {
            activity.toast(R.string.doublePressExit)
            firstTime = secondTime
            return
        } else {
            System.exit(0)
        }
    }

}
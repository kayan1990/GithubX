package com.kayan.github.model

import com.kayan.github.model.ui.UserUIModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * app全局数据对象
 * Created by kayan
 * Date: 2018-10-19
 */
@Singleton
class AppGlobalModel @Inject constructor() {
    val userObservable = UserUIModel()
}
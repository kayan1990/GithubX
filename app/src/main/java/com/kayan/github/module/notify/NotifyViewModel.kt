package com.kayan.github.module.notify

import android.app.Application
import android.content.Context
import com.kayan.github.module.base.BaseViewModel
import com.kayan.github.repository.UserRepository
import javax.inject.Inject

/**
 * Created by kayan
 * Date: 2018-11-12
 */

class NotifyViewModel @Inject constructor(application: Application, private val userRepository: UserRepository) : BaseViewModel(application) {

    var all: Boolean? = null
    var participating: Boolean? = null

    override fun loadDataByRefresh() {
        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadData() {
        userRepository.getNotify(all, participating, page, this)
    }

    fun setAllNotificationAsRead(context: Context) {
        userRepository.setAllNotificationAsRead(context)
    }

    fun setNotificationAsRead(id: String) {
        userRepository.setNotificationAsRead(id)
    }
}